package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //variable
    private CardView instructions_card, drivesafe_card, contactus_card, blahblah_card;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //hooks : defining
        //hooks for navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

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
        //continue from line 113 of this file.
        //card on click listener stops--------------------------------------------------------------

        //toolbar
        setSupportActionBar(toolbar);

        //navigation drawer menu
//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_login).setVisible(false);
//        menu.findItem(R.id.nav_logout).setVisible(true);

        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_dashboard);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen((GravityCompat.START))) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                Intent intent3 = new Intent(Dashboard.this, Dashboard.class);
                startActivity(intent3);
                break;

            case R.id.nav_admin:
                Intent intent2 = new Intent(Dashboard.this, AdminDashboard.class);
                startActivity(intent2);
                break;

            case R.id.nav_login:
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);
                break;

            case R.id.nav_update:
                Intent intent1 = new Intent(Dashboard.this, UserProfile.class);
                startActivity(intent1);
                break;

            case R.id.nav_logout:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
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
}