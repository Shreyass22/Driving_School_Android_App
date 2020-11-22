package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class TrainerAdProfile extends AppCompatActivity {
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

    TextInputLayout tr_name, tr_email, tr_phone, tr_password;
    RadioGroup gender_radio_btn;
    ProgressBar add_progess_bar;

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_ad_profile);

        tr_name = findViewById(R.id.tr_name);
        tr_email = findViewById(R.id.tr_email);
        tr_phone = findViewById(R.id.tr_phone);
        tr_password = findViewById(R.id.tr_password);
        gender_radio_btn = findViewById(R.id.gender_radio_btn);
        add_progess_bar = findViewById(R.id.add_progess_bar);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

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
    public void addData(View view) {

        String name = tr_name.getEditText().getText().toString();
        String email = tr_email.getEditText().getText().toString();
        String phone = tr_phone.getEditText().getText().toString();
        String password = tr_password.getEditText().getText().toString();
        int checkId = gender_radio_btn.getCheckedRadioButtonId();
        RadioButton selected_gender = gender_radio_btn.findViewById(checkId);
        if (selected_gender == null) {
            Toast.makeText(TrainerAdProfile.this, "Select Gender", Toast.LENGTH_SHORT).show();
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

    private void processinsert(String name, String email, String phone, String password, String gender) {
        add_progess_bar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                FirebaseUser rUser = firebaseAuth.getCurrentUser();
                String userID = rUser.getUid();
                reference = rootNode.getReference("trainer").child(userID); //realtimedb
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userId", userID);
                hashMap.put("name", name);
                hashMap.put("email", email);
                hashMap.put("phone", phone);
                hashMap.put("password", password);
                hashMap.put("gender", gender);
                // specify if the user is Client
                hashMap.put("isTrainer", "1");

//                df.set(hashMap).addOnCompleteListener(task1 -> {
//                    login_progess_bar.setVisibility(View.GONE);
//                    if (task1.isSuccessful()) {
//                        Intent intent = new Intent(SignUp.this,Dashboard.class);
//                        Toast.makeText(SignUp.this, "Registration Done", Toast.LENGTH_SHORT).show();
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else {
//                        Toast.makeText(SignUp.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    add_progess_bar.setVisibility(View.GONE);
                    if (task1.isSuccessful()) {
                        Intent intent = new Intent(TrainerAdProfile.this,TrainerAdminCard.class);
                        Toast.makeText(TrainerAdProfile.this, "Trainer Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(TrainerAdProfile.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                add_progess_bar.setVisibility(View.GONE);
                Toast.makeText(TrainerAdProfile.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //firestore
//        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
//            FirebaseUser rUser = firebaseAuth.getCurrentUser();
//            String userID = rUser.getUid();
//            Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//            DocumentReference df = fstore.collection("users").document(userID);
//            Map<String,String> userinfo = new HashMap<>();
//            userinfo.put("userId", userID);
//            userinfo.put("name", name);
//            userinfo.put("email", email);
//            userinfo.put("phone", phone);
//            userinfo.put("password", password);
//            userinfo.put("gender", gender);
//            userinfo.put("imageUrl", "default");
//            // specify if the user is admin
//            userinfo.put("isClient", "1");
//
//            df.set(userinfo).addOnCompleteListener(task1 -> {
//                login_progess_bar.setVisibility(View.GONE);
//                if (task1.isSuccessful()) {
//                    Intent intent = new Intent(SignUp.this,Dashboard.class);
//                    Toast.makeText(SignUp.this, "Registration", Toast.LENGTH_SHORT).show();
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                }
//                else {
//                    Toast.makeText(SignUp.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            //intent not created
//        });
    }

}