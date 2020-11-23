package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class InstructionsCard extends AppCompatActivity {

    private long backPressedTime;
    DrawerLayout drawerLayout;

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
        this.finish();
    }

    public void ClickInstructions(View view){
        recreate();
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
        Dashboard.redirectActivity(this,UserProfile.class);
        this.finish();
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
        logout(this);
    }

    public void logout(final Activity activity){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
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

    protected void onPause(){
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    //navigation drawer ends
}