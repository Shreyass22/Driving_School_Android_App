package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserProfile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextInputLayout full_name_profile, email_profile, phone_profile, password_profile;
    TextView full_name, user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        drawerLayout = findViewById(R.id.drawer_layout);

        //hooks
        full_name = findViewById(R.id.full_name);
        user_name = findViewById(R.id.user_name);
        full_name_profile = findViewById(R.id.full_name_profile);
        email_profile = findViewById(R.id.email_profile);
        phone_profile = findViewById(R.id.phone_profile);
        password_profile = findViewById(R.id.password_profile);

        //ShowAllUserData
        showAllUserData();
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        String user_Name = intent.getStringExtra("name");
        String user_username = intent.getStringExtra("username");
        String user_email = intent.getStringExtra("email");
        String user_phone = intent.getStringExtra("phone");
        String user_password = intent.getStringExtra("password");

        full_name.setText(user_Name);
        full_name_profile.getEditText().setText(user_Name);
        user_name.setText(user_username);
        email_profile.getEditText().setText(user_email);
        phone_profile.getEditText().setText(user_phone);
        password_profile.getEditText().setText(user_password);
    }

    //navigation drawer starts
    public void ClickMenu(View view){
        Dashboard.openDrawer(drawerLayout);
        this.finish();
    }

    public void ClickLogo(View view){
        Dashboard.closeDrawer(drawerLayout);
        this.finish();
    }

    public void ClickDashboard(View view){
        Dashboard.redirectActivity(this,Dashboard.class);
        this.finish();
    }

    public void ClickInstructions(View view){
        Dashboard.redirectActivity(this,InstructionsCard.class);
        this.finish();
    }

    public void ClickAdmin(View view){
        Dashboard.redirectActivity(this,AdminDashboard.class);
        this.finish();
    }

    public void ClickTrainer(View view){
        Dashboard.redirectActivity(this,Trainer.class);
        this.finish();
    }

    public void ClickClient(View view){
        Dashboard.redirectActivity(this,Client.class);
        this.finish();
    }

    public void ClickLogin(View view){
        Dashboard.redirectActivity(this,Login.class);
        this.finish();
    }

    public void ClickUpdate(View view){
        recreate();
    }

    public void ClickAboutus(View view){
        Dashboard.redirectActivity(this,ContactusCard.class);
        this.finish();
    }

    public void ClickRate(View view){
        Dashboard.redirectActivity(this,Rate.class);
        this.finish();
    }

    public void ClickLogout(View view){
        Dashboard.logout(this);
    }

    protected void onPause(){
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    //navigation drawer ends
}