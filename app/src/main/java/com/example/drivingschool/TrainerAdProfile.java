package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class TrainerAdProfile extends Fragment {
    private static final Pattern Password_Pattern =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private long backPressedTime;
    private DrawerLayout drawerLayout;
    private TextInputLayout tr_name, tr_email, tr_phone, tr_password;
    private RadioGroup gender_radio_btn;
    private ProgressBar add_progess_bar;
    private Button addData;

    private FirebaseDatabase rootNode;
    private FirebaseAuth firebaseAuth,firebaseAuth1;
    private DatabaseReference reference;
    private FirebaseAuth mAuth2;
    private FirebaseUser user;

//    @Override
//    public void onBackPressed() {
//        if (backPressedTime + 3000 > System.currentTimeMillis()){
//            super.onBackPressed();
////            System.exit(0);
//            return;
//        }
//        else {
//            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        backPressedTime = System.currentTimeMillis();
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_trainer_ad_profile, container, false);

        drawerLayout = rootView.findViewById(R.id.drawer_layout);



        tr_name = rootView.findViewById(R.id.tr_name);
        tr_email = rootView.findViewById(R.id.tr_email);
        tr_phone = rootView.findViewById(R.id.tr_phone);
        tr_password = rootView.findViewById(R.id.tr_password);
        gender_radio_btn = rootView.findViewById(R.id.gender_radio_btn);
        add_progess_bar = rootView.findViewById(R.id.add_progess_bar);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://driving-school-bbc95.firebaseio.com/")             //[Database_url_here]
                .setApiKey("AIzaSyANIAdCPJ_ycRg08blcu24u4uoteP3_SFs")                       //Web_API_KEY_HERE
                .setApplicationId("driving-school-bbc95").build();                          //PROJECT_ID_HERE

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getContext(), firebaseOptions, "Driving School");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("Driving School"));
        }

        addData = rootView.findViewById(R.id.add_btn22);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = tr_name.getEditText().getText().toString();
                String email = tr_email.getEditText().getText().toString();
                String phone = tr_phone.getEditText().getText().toString();
                String password = tr_password.getEditText().getText().toString();
                int checkId = gender_radio_btn.getCheckedRadioButtonId();
                RadioButton selected_gender = gender_radio_btn.findViewById(checkId);
                if (selected_gender == null) {
                    Toast.makeText(getContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                }
                else {
                    final String gender = selected_gender.getText().toString();
                    //validate
                    if(!validateName() | !validateEmail() | !validatePhone() | !validatePassword()) {
                        return;
                    }
                    else{
                        processinsert(name, email, phone, password, gender);
                    }
                }
            }
        });

        return rootView;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trainer_ad_profile);
//            }

    private Boolean validateName () {
        String val = tr_name.getEditText().getText().toString();
        if(val.isEmpty()) {
            tr_name.setError("Field cannot be empty");
            return false;
        }
        else{
            tr_name.setError(null);
            tr_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail () {
        String val = tr_email.getEditText().getText().toString();

        if(val.isEmpty()) {
            tr_email.setError("Field cannot be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            tr_email.setError("Enter valid E-mail");
            return false;
        }
        else{
            tr_email.setError(null);
            tr_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone () {
        String val = tr_phone.getEditText().getText().toString();
        if(val.isEmpty()) {
            tr_phone.setError("Field cannot be empty");
            return false;
        }
        else{
            tr_phone.setError(null);
            tr_phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword () {
        String val = tr_password.getEditText().getText().toString();
        if(val.isEmpty()) {
            tr_password.setError("Field cannot be empty");
            return false;
        }
        else if (!Password_Pattern.matcher(val).matches()) {
            tr_password.setError("Password is too weak");
            return false;
        }
        else{
            tr_password.setError(null);
            tr_password.setErrorEnabled(false);
            return true;
        }
    }


    //save data in firebase on button click
//    public void addData(View view) {
//    }

    private void processinsert(String name, String email, String phone, String password, String gender) {
        add_progess_bar.setVisibility(View.VISIBLE);

        mAuth2.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                FirebaseUser rUser = mAuth2.getCurrentUser();
//                firebaseAuth1.signOut();
                String userID = rUser.getUid();

                reference = rootNode.getReference("users").child(userID); //realtimedb
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userId", userID);
                hashMap.put("name", name);
                hashMap.put("email", email);
                hashMap.put("phone", phone);
                hashMap.put("password", password);
                hashMap.put("gender", gender);
                // specify if the user is Trainer
                hashMap.put("type", "Trainer");


                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    add_progess_bar.setVisibility(View.VISIBLE);
                    if (task1.isSuccessful()) {
//                        firebaseAuth.updateCurrentUser(user);
//                        Log.d("TAG1", "processinsert: "+firebaseAuth.getCurrentUser().getUid().toString());
//                        Fragment fm = new Fragment();
//                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                        ft.replace(R.id.drawer_layout, new TrainerAdminCard()).commit();
//                        Toast.makeText(getContext(), "Trainer Registration Successful", Toast.LENGTH_SHORT).show();
                        //finish();
                        mAuth2.signOut();
                    }
                    else {
                        add_progess_bar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                add_progess_bar.setVisibility(View.GONE);
                Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}