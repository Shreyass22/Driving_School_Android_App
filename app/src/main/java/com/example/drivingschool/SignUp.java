package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
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

    TextInputLayout reg_name, reg_email, reg_phone, reg_password;
    Button reg_btn, btn_login;
    RadioGroup gender_radio_btn;
    ProgressBar login_progess_bar;

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseFirestore fstore;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        //Hooks
        reg_name = findViewById(R.id.reg_name);
        reg_email = findViewById(R.id.reg_email);
        reg_phone = findViewById(R.id.reg_phone);
        reg_password = findViewById(R.id.reg_password);
        reg_btn = findViewById(R.id.reg_btn);
        btn_login = findViewById(R.id.btn_login);
        gender_radio_btn = findViewById(R.id.gender_radio_btn);
        login_progess_bar = findViewById(R.id.login_progess_bar);
    }

    public void btn_login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    private Boolean validateName () {
        String val = reg_name.getEditText().getText().toString();
        if(val.isEmpty()) {
            reg_name.setError("Field cannot be empty");
            return false;
        }
        else{
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail () {
        String val = reg_email.getEditText().getText().toString();

        if(val.isEmpty()) {
            reg_email.setError("Field cannot be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            reg_email.setError("Enter valid E-mail");
            return false;
        }
        else{
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone () {
        String val = reg_phone.getEditText().getText().toString();
        if(val.isEmpty()) {
            reg_phone.setError("Field cannot be empty");
            return false;
        }
        else{
            reg_phone.setError(null);
            reg_phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword () {
        String val = reg_password.getEditText().getText().toString();
        if(val.isEmpty()) {
            reg_password.setError("Field cannot be empty");
            return false;
        }
        else if (!Password_Pattern.matcher(val).matches()) {
            reg_password.setError("Password is too weak");
            return false;
        }
        else{
            reg_password.setError(null);
            reg_password.setErrorEnabled(false);
            return true;
        }
    }



    //save data in firebase on button click
    public void reg_btn(View view) {

        String name = reg_name.getEditText().getText().toString();
        String email = reg_email.getEditText().getText().toString();
        String phone = reg_phone.getEditText().getText().toString();
        String password = reg_password.getEditText().getText().toString();
        int checkId = gender_radio_btn.getCheckedRadioButtonId();
        RadioButton selected_gender = gender_radio_btn.findViewById(checkId);
        if (selected_gender == null) {
            Toast.makeText(SignUp.this, "Select Gender", Toast.LENGTH_SHORT).show();
        }
        else {
            final String gender = selected_gender.getText().toString();
            //validate
            if(!validateName() | !validateEmail() | !validatePhone() | !validatePassword()) {
                return;
            }
            else{
                register(name, email, phone, password, gender);
            }
        }
    }

    private void register(String name, String email, String phone, String password, String gender) {
        login_progess_bar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                FirebaseUser rUser = firebaseAuth.getCurrentUser();
                String userID = rUser.getUid();
                reference = rootNode.getReference("users").child(userID); //realtimedb
                //DocumentReference df = fstore.collection("users").document(userID); //fstore
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userId", userID);
                hashMap.put("name", name);
                hashMap.put("email", email);
                hashMap.put("phone", phone);
                hashMap.put("password", password);
                hashMap.put("gender", gender);
                // specify if the user is Client
                hashMap.put("isClient", "1");

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
                    login_progess_bar.setVisibility(View.GONE);
                    if (task1.isSuccessful()) {
                        Intent intent = new Intent(SignUp.this,Dashboard.class);
                        Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SignUp.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                login_progess_bar.setVisibility(View.GONE);
                Toast.makeText(SignUp.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

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