package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class ClientAdProfile extends AppCompatActivity {
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
    DrawerLayout drawerLayout;
    TextInputLayout cl_name, cl_email, cl_phone, cl_password;
    RadioGroup gender_radio_btn;
    ProgressBar add_progess_bar;

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()){
            super.onBackPressed();
//            System.exit(0);
            return;
        }
        else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_ad_profile);
        drawerLayout = findViewById(R.id.drawer_layout);



        cl_name = findViewById(R.id.cl_name);
        cl_email = findViewById(R.id.cl_email);
        cl_phone = findViewById(R.id.cl_phone);
        cl_password = findViewById(R.id.cl_password);
        gender_radio_btn = findViewById(R.id.gender_radio_btn);
        add_progess_bar = findViewById(R.id.add_progess_bar);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private Boolean validateName () {
        String val = cl_name.getEditText().getText().toString();
        if(val.isEmpty()) {
            cl_name.setError("Field cannot be empty");
            return false;
        }
        else{
            cl_name.setError(null);
            cl_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail () {
        String val = cl_email.getEditText().getText().toString();

        if(val.isEmpty()) {
            cl_email.setError("Field cannot be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            cl_email.setError("Enter valid E-mail");
            return false;
        }
        else{
            cl_email.setError(null);
            cl_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone () {
        String val = cl_phone.getEditText().getText().toString();
        if(val.isEmpty()) {
            cl_phone.setError("Field cannot be empty");
            return false;
        }
        else{
            cl_phone.setError(null);
            cl_phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword () {
        String val = cl_password.getEditText().getText().toString();
        if(val.isEmpty()) {
            cl_password.setError("Field cannot be empty");
            return false;
        }
        else if (!Password_Pattern.matcher(val).matches()) {
            cl_password.setError("Password is too weak");
            return false;
        }
        else{
            cl_password.setError(null);
            cl_password.setErrorEnabled(false);
            return true;
        }
    }

    //save data in firebase on button click
    public void addDataCl(View view) {

        String name = cl_name.getEditText().getText().toString();
        String email = cl_email.getEditText().getText().toString();
        String phone = cl_phone.getEditText().getText().toString();
        String password = cl_password.getEditText().getText().toString();
        int checkId = gender_radio_btn.getCheckedRadioButtonId();
        RadioButton selected_gender = gender_radio_btn.findViewById(checkId);
        if (selected_gender == null) {
            Toast.makeText(ClientAdProfile.this, "Select Gender", Toast.LENGTH_SHORT).show();
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
                reference = rootNode.getReference("users").child(userID); //realtimedb
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userId", userID);
                hashMap.put("name", name);
                hashMap.put("email", email);
                hashMap.put("phone", phone);
                hashMap.put("password", password);
                hashMap.put("gender", gender);
                // specify if the user is client
                hashMap.put("isClient", "1");


                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    add_progess_bar.setVisibility(View.GONE);
                    if (task1.isSuccessful()) {
                        Intent intent = new Intent(ClientAdProfile.this,ClientAdminCard.class);
                        Toast.makeText(ClientAdProfile.this, "Client Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(ClientAdProfile.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                add_progess_bar.setVisibility(View.GONE);
                Toast.makeText(ClientAdProfile.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
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
        Dashboard.redirectActivity(this, ContactusCard.class);
        this.finish();
    }

    public void ClickAboutus(View view) {
        Dashboard.redirectActivity(this, ContactusCard.class);
        this.finish();
    }

    public void ClickRate(View view) {
        Dashboard.redirectActivity(this, Rate.class);
        this.finish();
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public void logout(final Activity activity){
        android.app.AlertDialog.Builder builder= new AlertDialog.Builder(activity);
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