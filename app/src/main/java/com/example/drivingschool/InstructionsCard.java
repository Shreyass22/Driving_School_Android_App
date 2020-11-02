package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class InstructionsCard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_card);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    //navigation drawer starts
    public void ClickMenu(View view){
        Dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickDashboard(View view){
        Dashboard.redirectActivity(this,Dashboard.class);
    }

    public void ClickInstructions(View view){
        recreate();
    }

    public void ClickAdmin(View view){
        Dashboard.redirectActivity(this,AdminDashboard.class);
    }

    public void ClickTrainer(View view){
        Dashboard.redirectActivity(this,Trainer.class);
    }

    public void ClickClient(View view){
        Dashboard.redirectActivity(this,Client.class);
    }

    public void ClickLogin(View view){
        Dashboard.redirectActivity(this,Login.class);
    }

    public void ClickUpdate(View view){
        Dashboard.redirectActivity(this,UserProfile.class);
    }

    public void ClickAboutus(View view){
        Dashboard.redirectActivity(this,ContactusCard.class);
    }

    public void ClickRate(View view){
        Dashboard.redirectActivity(this,Rate.class);
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