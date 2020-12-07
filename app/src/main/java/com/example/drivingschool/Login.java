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
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Login extends AppCompatActivity {

    Button login_btn, btn_signup, forget_password;
    ImageView imageView;
    TextView logo_name, slogan_name;
    TextInputLayout login_e_mail, login_password;
    ProgressBar login_progess_bar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Switch active;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        imageView = findViewById(R.id.imageView);
        logo_name = findViewById(R.id.logo_name);
        slogan_name = findViewById(R.id.slogan_name);
        login_e_mail = findViewById(R.id.login_e_mail);
        login_password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        btn_signup = findViewById(R.id.btn_signup);
        login_progess_bar = findViewById(R.id.login_progess_bar);
        forget_password = findViewById(R.id.forget_password);
//        active = findViewById(R.id.active);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
        }

        forget_password.setOnClickListener(v -> startActivity(new Intent(Login.this, ForgetPassword.class)));


    }

    private Boolean validateUsername() {
        String val = login_e_mail.getEditText().getText().toString();
        if (val.isEmpty()) {
            login_e_mail.setError("Field cannot be empty");
            return false;
        } else {
            login_e_mail.setError(null);
            login_e_mail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = login_password.getEditText().getText().toString();
        if (val.isEmpty()) {
            login_password.setError("Field cannot be empty");
            return false;
        } else {
            login_password.setError(null);
            login_password.setErrorEnabled(false);
            return true;
        }
    }

    //animation
    public void btn_signup(View view) {
        //animation start
        Intent intent = new Intent(Login.this, SignUp.class);
        this.finish();
        Pair[] pairs = new Pair[6];
        pairs[0] = new Pair<View, String>(imageView, "logo_image");
        pairs[1] = new Pair<View, String>(logo_name, "logo_name");
        pairs[2] = new Pair<View, String>(slogan_name, "slogan_name");
        pairs[3] = new Pair<View, String>(login_e_mail, "login_e_mail");
        pairs[4] = new Pair<View, String>(login_password, "login_password");
        pairs[5] = new Pair<View, String>(login_btn, "login_btn");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, options.toBundle());
        //animation end
    }


    //check validation
    public void login_btn(View view) {
        String userEnteredName = login_e_mail.getEditText().getText().toString().trim();
        String userEnteredPassword = login_password.getEditText().getText().toString().trim();
        //validate
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            login(userEnteredName, userEnteredPassword);
        }
    }

    private void login(String userEnteredName, String userEnteredPassword) {
        login_progess_bar.setVisibility(View.VISIBLE);
        //RDB
        firebaseAuth.signInWithEmailAndPassword(userEnteredName, userEnteredPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser rUser = firebaseAuth.getCurrentUser(); //
                String userID = rUser.getUid();  //
                databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child(userID).child("type").getValue(String.class).equals("Admin")) {

//                            preferences.setDataLogin(Login.this, true);
//                            preferences.setDataAs(Login.this, "Admin");
                            startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
                        } else if (snapshot.child(userID).child("type").getValue(String.class).equals("Trainer")) {

//                            preferences.setDataLogin(Login.this, true);
//                            preferences.setDataAs(Login.this, "Trainer");
                            startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
                        } else if (snapshot.child(userID).child("type").getValue(String.class).equals("Client")) {

//                            preferences.setDataLogin(Login.this, true);
//                            preferences.setDataAs(Login.this, "Client");
                            startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
                        } else {
                            login_progess_bar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Email doesnot exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        login_progess_bar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "ERROR : 404", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                login_progess_bar.setVisibility(View.GONE);
                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //onstart : when app starts
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }

    }
}