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

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variable
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        //hooks : defining
        //hooks for navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //toolbar
//        setSupportActionBar(toolbar);

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
                Intent intent3 = new Intent(NavigationDrawer.this, Dashboard.class);
                startActivity(intent3);
                break;
            case R.id.nav_admin:
                Intent intent2 = new Intent(NavigationDrawer.this, AdminDashboard.class);
                startActivity(intent2);
                break;
            case R.id.nav_instructions:
                Intent intent4 = new Intent(NavigationDrawer.this, InstructionsCard.class);
                startActivity(intent4);
                break;
            case R.id.nav_aboutus:
                Intent intent5 = new Intent(NavigationDrawer.this, ContactusCard.class);
                startActivity(intent5);
                break;
            case R.id.nav_map:
                Intent intent9 = new Intent(NavigationDrawer.this, Map.class);
                startActivity(intent9);
                break;
            case R.id.nav_login:
                Intent intent = new Intent(NavigationDrawer.this, Login.class);
                startActivity(intent);
                break;
            case R.id.nav_update:
                Intent intent1 = new Intent(NavigationDrawer.this, UserProfile.class);
                startActivity(intent1);
                break;
            case R.id.nav_logout:
                break;
            case R.id.nav_trainer:
                break;
            case R.id.nav_rate:
                Intent intent6 = new Intent(NavigationDrawer.this, Rate.class);
                startActivity(intent6);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}