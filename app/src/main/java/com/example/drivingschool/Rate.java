package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;

import java.util.HashMap;
import java.util.Objects;

public class Rate extends AppCompatActivity {

    TextView feedback_name, feedback_mail, feedback_type;
    EditText feedback_msg;
    private long backPressedTime;
    DrawerLayout drawerLayout;
    Button submit_feedback;
    ProgressBar progess_barrr;
    String feedback;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference databaseReference;
    private UserHelperClass usersData;

    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            System.exit(0);
            startActivity(new Intent(Rate.this, NavigationDrawer.class));
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.activity_rate, container, false);
//        return rootView;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        drawerLayout = findViewById(R.id.drawer_layout);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        feedback_name = findViewById(R.id.feedback_name);
        feedback_mail = findViewById(R.id.feedback_mail);
        feedback_msg = findViewById(R.id.feedback_msg);
        feedback_type = findViewById(R.id.feedback_type);
        submit_feedback = findViewById(R.id.submit_feedback);
        progess_barrr = findViewById(R.id.progess_barrr);

        //rate
        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {

                switch (smiley) {
                    case SmileRating.TERRIBLE:
                        feedback = "TERRIBLE";
                        Toast.makeText(getApplicationContext(), "TERRIBLE", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.BAD:
                        feedback = "BAD";
                        Toast.makeText(getApplicationContext(), "BAD", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        feedback = "OKAY";
                        Toast.makeText(getApplicationContext(), "OKAY", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        feedback = "GOOD";
                        Toast.makeText(getApplicationContext(), "GOOD", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        feedback = "GREAT";
                        Toast.makeText(getApplicationContext(), "GREAT", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                Toast.makeText(getApplicationContext(), "Rating level "+ level, Toast.LENGTH_SHORT).show();
                //feedback = level;
            }
        });
        //rate


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
                feedback_name.setText((usersData.getName()));
                feedback_mail.setText((usersData.getEmail()));
                feedback_type.setText((usersData.getType()));
//                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
            }
        });

        submit_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = feedback_name.getText().toString();
                String mail = feedback_mail.getText().toString();
                String type = feedback_type.getText().toString();
                String msg = feedback_msg.getText().toString();

                processinsertFeed(name, mail, type, msg);
            }
        });
    }

//    public void submit_feedback(View view) {
//
//
//    }

    private void processinsertFeed(String name, String mail, String type, String msg) {
        progess_barrr.setVisibility(View.VISIBLE);
        FirebaseUser rUser = firebaseAuth.getCurrentUser(); //
        String userID = rUser.getUid();  //
        databaseReference = rootNode.getReference("feedback").child(userID); //realtimedb
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("mail", mail);
        hashMap.put("type", type);
        hashMap.put("message", msg);
        hashMap.put("level", feedback);

        databaseReference.setValue(hashMap).addOnCompleteListener(task1 -> {
            progess_barrr.setVisibility(View.GONE);
            if (task1.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Thanks for FeedBack", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}