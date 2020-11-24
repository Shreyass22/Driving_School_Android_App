package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile<TaskUri> extends AppCompatActivity {

    private long backPressedTime;
    private DrawerLayout drawerLayout;
    private TextInputLayout full_name_profile, email_profile, phone_profile, password_profile;
    private TextView full_name, email_profilee, type_profilee;
    private CircleImageView profile_user;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;
    private Uri imageUri;
    private String myUri = "";
    private StorageReference storageReference;
    private UserHelperClass usersData;
    private Button update_data;

    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed();
//            System.exit(0);
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        drawerLayout = findViewById(R.id.drawer_layout);


        //hooks
        full_name = findViewById(R.id.full_name);
        email_profilee = findViewById(R.id.email_profilee);
        full_name_profile = findViewById(R.id.full_name_profile);
        type_profilee = findViewById(R.id.type_profilee);
        email_profile = findViewById(R.id.email_profile);
        phone_profile = findViewById(R.id.phone_profile);
        password_profile = findViewById(R.id.password_profile);
        profile_user = findViewById(R.id.profile_user);
        update_data = findViewById(R.id.update_data);

        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("client1", "onDataChange: " + snap2.toString());
//                if (snapshot.getKey().equals(firebaseUser.getUid())) {
                    Log.d("client2", "HEllo" + snapshot.getValue().toString());
                    usersData = snapshot.getValue(UserHelperClass.class);
                    assert usersData != null;
                    email_profilee.setText(usersData.getEmail());
                    full_name.setText(usersData.getName());
                    type_profilee.setText(usersData.getType());
                    full_name_profile.getEditText().setText(usersData.getName());
                    email_profile.getEditText().setText((usersData.getEmail()));
                    phone_profile.getEditText().setText((usersData.getPhone()));
                    password_profile.getEditText().setText((usersData.getPassword()));
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT);
            }
        });


        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
            }
        });

        profile_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1, 1).start(UserProfile.this);
            }
        });

        getImageInfo();
    }

    private void getImageInfo() {
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    if (snapshot.hasChild("image")) {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profile_user);
                    }
                }
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    for (DataSnapshot snap2 : snap.getChildren()) {
//                        if (snap2.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
//                            if (snap2.exists() && snap2.getChildrenCount() > 0) {
//                                if (snap2.hasChild("image")) {
//                                    String image = snap2.child("image").getValue().toString();
//                                    Picasso.get().load(image).into(profile_user);
//                                }
//                            }
//                        }
//                    }
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profile_user.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error, try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Set your Profile");
        progressDialog.setMessage("Please wait, While we are setting your data");
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileRef = storageReference.child(firebaseAuth.getUid() + ".jpg");
            storageTask = fileRef.putFile(imageUri);

            storageTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image", myUri);

                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).updateChildren(userMap);
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }

    }

    //navigation drawer starts
    public void ClickMenu(View view) {
        Dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickDashboard(View view) {
        Dashboard.redirectActivity(this, Dashboard.class);
        this.finish();
    }

    public void ClickInstructions(View view) {
        Dashboard.redirectActivity(this, InstructionsCard.class);
        this.finish();
    }

    public void ClickAdmin(View view) {
        Dashboard.redirectActivity(this, AdminDashboard.class);
        this.finish();
    }

    public void ClickTrainer(View view) {
        Dashboard.redirectActivity(this, Trainer.class);
        this.finish();
    }

    public void ClickClient(View view) {
        Dashboard.redirectActivity(this, Client.class);
        this.finish();
    }

    public void ClickLogin(View view) {
        Dashboard.redirectActivity(this, Login.class);
        this.finish();
    }

    public void ClickUpdate(View view) {
        recreate();
    }

    public void ClickAboutus(View view) {
        Dashboard.redirectActivity(this, ContactusCard.class);
        this.finish();
    }

    public void ClickRate(View view) {
        Dashboard.redirectActivity(this, Rate.class);
        this.finish();
    }

    public void ClickLogout(View view) {
        logout(this);
    }

    public void logout(final Activity activity) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(((Dialog) dialog).getContext(), Login.class);
                startActivity(myIntent);
                return;
            }
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    protected void onPause() {
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    //navigation drawer ends
}