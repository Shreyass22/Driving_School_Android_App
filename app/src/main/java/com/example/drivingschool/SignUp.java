package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    TextInputLayout reg_name, reg_username, reg_email, reg_phone, reg_password, reg_male, reg_female;
    Button reg_btn, btn_login;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        reg_name = findViewById(R.id.reg_name);
        reg_username = findViewById(R.id.reg_username);
        reg_email = findViewById(R.id.reg_email);
        reg_phone = findViewById(R.id.reg_phone);
        reg_password = findViewById(R.id.reg_password);
//        reg_male = findViewById(R.id.reg_male);
//        reg_female = findViewById(R.id.reg_female);
        reg_btn = findViewById(R.id.reg_btn);
        btn_login = findViewById(R.id.btn_login);
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

    private Boolean validateUsername () {
        String val = reg_username.getEditText().getText().toString();
//        String noWhiteSpaces = "(?=\\s+$)";
        if(val.isEmpty()) {
            reg_username.setError("Field cannot be empty");
            return false;
        }
        else if (val.length() > 15){
            reg_username.setError("Username too long");
            return false;
        }
//        else if (!val.matches(noWhiteSpaces)){
//            reg_username.setError("white spaces not allowed");
//            return false;
//        }
        else{
            reg_username.setError(null);
            reg_username.setErrorEnabled(false);
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

        //validate
        if(!validateName() | !validateUsername() | !validateEmail() | !validatePhone() | !validatePassword()) {
            return;
        }
        //db
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        String name = reg_name.getEditText().getText().toString();
        String username = reg_username.getEditText().getText().toString();
        String email = reg_email.getEditText().getText().toString();
        String phone = reg_phone.getEditText().getText().toString();
        String password = reg_password.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phone, password);
        reference.child(username).setValue(helperClass);

        Toast t1 = Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT);
        t1.show();

        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}