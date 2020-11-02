package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Login extends AppCompatActivity {

    Button login_btn, btn_signup;
    ImageView imageView;
    TextView logo_name, slogan_name;
    TextInputLayout login_username, login_password;
    String passwordFromDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        imageView = findViewById(R.id.imageView);
        logo_name = findViewById(R.id.logo_name);
        slogan_name = findViewById(R.id.slogan_name);
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        btn_signup = findViewById(R.id.btn_signup);

    }

    private Boolean validateUsername () {
        String val = login_username.getEditText().getText().toString();
        if(val.isEmpty()) {
            login_username.setError("Field cannot be empty");
            return false;
        }
        else{
            login_username.setError(null);
            login_username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword () {
        String val = login_password.getEditText().getText().toString();
        if(val.isEmpty()) {
            login_password.setError("Field cannot be empty");
            return false;
        }
        else{
            login_password.setError(null);
            login_password.setErrorEnabled(false);
            return true;
        }
    }

    public void btn_signup(View view) {
        //animation start
        Intent intent = new Intent(Login.this, SignUp.class);
        this.finish();
        Pair[] pairs = new Pair[6];
        pairs[0] = new Pair<View,String>(imageView,"logo_image");
        pairs[1] = new Pair<View,String>(logo_name,"logo_name");
        pairs[2] = new Pair<View,String>(slogan_name,"slogan_name");
        pairs[3] = new Pair<View,String>(login_username,"login_username");
        pairs[4] = new Pair<View,String>(login_password,"login_password");
        pairs[5] = new Pair<View,String>(login_btn,"login_btn");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent,options.toBundle());
        //animation end
    }

    public void login_btn(View view) {
        //db
        //validate
        if(!validateUsername() | !validatePassword()) {
            return;
        }
        else{
            isUser();
            Toast t = Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT);
            t.show();
        }
        //db end
    }

    private void isUser() {

        String userEnteredName = login_username.getEditText().getText().toString().trim();
        String userEnteredPassword = login_password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d("tag1", "Value is: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("2", "Failed to read value.", error.toException());
            }
        });
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredName);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                        passwordFromDB = (String) messageSnapshot.child("password").getValue();
                        //Log.d("pass", "onDataChange: "+passwordFromDB);
                    }
                    login_username.setError(null);
                    login_username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredName).child("password").getValue(String.class);

//                    Log.d("TAG", "onDataChange:" + passwordFromDB);
                    if (userEnteredPassword.equals(passwordFromDB)){
                        

                        login_username.setError(null);
                        login_username.setErrorEnabled(false);

                        String nameFromDB = snapshot.child(userEnteredName).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredName).child("username").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredName).child("email").getValue(String.class);
                        String phoneFromDB = snapshot.child(userEnteredName).child("phone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    }
                    else {
                        login_password.setError("Wrong Password");
                        login_password.requestFocus();
                    }
                }
                else {
                    login_username.setError("No such User exit");
                    login_username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}