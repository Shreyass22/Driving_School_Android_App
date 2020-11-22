package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{

    //variable
    private CardView instructions_card, drivesafe_card, contactus_card, blahblah_card;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //hooks : defining
        //hooks for navigation
        drawerLayout = findViewById(R.id.drawer_layout);

        //card on click listener starts-------------------------------------------------------------
        //hooks for cardview
        instructions_card = (CardView) findViewById(R.id.instructions_card);
        drivesafe_card = (CardView) findViewById(R.id.drivesafe_card);
        contactus_card = (CardView) findViewById(R.id.contactus_card);
        blahblah_card = (CardView) findViewById(R.id.blahblah_card);
        //Add Click listener to the cards
        instructions_card.setOnClickListener(this);
        drivesafe_card.setOnClickListener(this);
        contactus_card.setOnClickListener(this);
        blahblah_card.setOnClickListener(this);
        //continue from line 123 of this file.
        //card on click listener stops--------------------------------------------------------------
    }
    // onClick event on card
    @Override
    public void onClick(View v) {
        Intent c, c1, c2, c3;
        switch (v.getId()) {
            case R.id.instructions_card : c = new Intent(this, InstructionsCard.class); startActivity(c); break;
            case R.id.drivesafe_card : c1 = new Intent(this, DrivesafeCard.class); startActivity(c1); break;
            case R.id.contactus_card : c2 = new Intent(this, ContactusCard.class); startActivity(c2); break;
            case R.id.blahblah_card : c3 = new Intent(this, BlahblahCard.class); startActivity(c3); break;
            default: break;
        }
    }
    // onClick event on card end


    //Navigation drawer starts
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickDashboard(View view){
        recreate();
    }

    public void ClickInstructions(View view){
        redirectActivity(this,InstructionsCard.class);
        this.finish();
    }

    public void ClickAdmin(View view){
        redirectActivity(this,AdminDashboard.class);
        this.finish();
    }

    public void ClickTrainer(View view){
        redirectActivity(this,Trainer.class);
        this.finish();
    }

    public void ClickClient(View view){
        redirectActivity(this,Client.class);
        this.finish();
    }

    public void ClickLogin(View view){
        redirectActivity(this,Login.class);
        this.finish();
    }

    public void ClickUpdate(View view){
        redirectActivity(this,UserProfile.class);
        this.finish();
    }

    public void ClickRate(View view){
        redirectActivity(this,Rate.class);
        this.finish();
    }

    public void ClickAboutus(View view){
        redirectActivity(this,ContactusCard.class);
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

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
    //Navigation drawer ends
}