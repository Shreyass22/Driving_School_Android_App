package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //login logout
    boolean isLoggedIn = false;
    //variable
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    FirebaseUser rUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        rUser = firebaseAuth.getCurrentUser();

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
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_dashboard);

        updateUI();
    }

    private void updateUI() {

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String userID = rUser.getUid();
        Log.d("tag1", "onPrepareOptionsMenu: ");
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(userID).child("type").getValue(String.class).equals("Admin")) {

                    menu.removeItem(R.id.nav_login);
                    menu.removeItem(R.id.nav_trainer);
                    menu.removeItem(R.id.nav_client);
                    Log.d("TAG", "onDataChange:laaaaaaaaaa ");
                } else if (snapshot.child(userID).child("type").getValue(String.class).equals("Trainer")) {

                    menu.removeItem(R.id.nav_login);
                    menu.removeItem(R.id.nav_admin);
                    menu.removeItem(R.id.nav_client);
                } else if (snapshot.child(userID).child("type").getValue(String.class).equals("Client")) {

                    menu.removeItem(R.id.nav_login);
                    menu.removeItem(R.id.nav_trainer);
                    menu.removeItem(R.id.nav_admin);
                } else {
                    Toast.makeText(NavigationDrawer.this, "Email doesnot exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NavigationDrawer.this, "ERROR : 404", Toast.LENGTH_SHORT).show();
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen((GravityCompat.START))) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
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