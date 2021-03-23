package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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

//    FragmentManager fragmentManager;
//    FragmentTransaction fragmentTransaction;

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
        Menu nav_Menu = navigationView.getMenu();
        toolbar = findViewById(R.id.toolbar);

        //toolbar
        //setSupportActionBar(toolbar);

        //navigation drawer menu
//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_login).setVisible(false);
//        menu.findItem(R.id.nav_logout).setVisible(true);

        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.setDrawerIndicatorEnabled(true);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setCheckedItem(R.id.nav_dashboard);

//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, new Dashboard());


//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new Dashboard()).commit();

        FirebaseUser rUser = firebaseAuth.getCurrentUser(); //
        String userID = rUser.getUid();  //
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(userID).child("type").getValue(String.class).equals("Admin")) {

                    loadFragment(new AdminDashboard());
                    nav_Menu.findItem(R.id.nav_trainer).setVisible(false);
                    nav_Menu.findItem(R.id.nav_client).setVisible(false);
                    nav_Menu.findItem(R.id.nav_login).setVisible(false);
                    nav_Menu.findItem(R.id.nav_rate).setVisible(false);
                } else if (snapshot.child(userID).child("type").getValue(String.class).equals("Trainer")) {

                    loadFragment(new Trainer());
                    nav_Menu.findItem(R.id.nav_admin).setVisible(false);
                    nav_Menu.findItem(R.id.nav_client).setVisible(false);
                    nav_Menu.findItem(R.id.nav_login).setVisible(false);
                    nav_Menu.findItem(R.id.nav_rate).setVisible(false);
                    nav_Menu.findItem(R.id.rate_dashh).setVisible(false);
                } else if (snapshot.child(userID).child("type").getValue(String.class).equals("Client")) {

                    loadFragment(new Client());
                    nav_Menu.findItem(R.id.nav_trainer).setVisible(false);
                    nav_Menu.findItem(R.id.nav_admin).setVisible(false);
                    nav_Menu.findItem(R.id.nav_login).setVisible(false);
                    nav_Menu.findItem(R.id.rate_dashh).setVisible(false);
                } else {
                    Toast.makeText(getApplicationContext(), "Email doesnot exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "ERROR : 404", Toast.LENGTH_SHORT).show();
            }
        });

//        updateUI();
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

//    private void updateUI() {
////        invalidateOptionsMenu();
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        String userID = rUser.getUid();
//        Log.d("tag1", "onPrepareOptionsMenu: ");
//        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.child(userID).child("type").getValue(String.class).equals("Admin")) {
//
//                    menu.removeItem(R.id.nav_login);
//                    menu.removeItem(R.id.nav_trainer);
//                    menu.removeItem(R.id.nav_client);
//
//                } else if (snapshot.child(userID).child("type").getValue(String.class).equals("Trainer")) {
//
//                    menu.removeItem(R.id.nav_login);
//                    menu.removeItem(R.id.nav_admin);
//                    menu.removeItem(R.id.nav_client);
//                } else if (snapshot.child(userID).child("type").getValue(String.class).equals("Client")) {
//
//                    menu.removeItem(R.id.nav_login);
//                    menu.removeItem(R.id.nav_trainer);
//                    menu.removeItem(R.id.nav_admin);
//                } else {
//                    Toast.makeText(NavigationDrawer.this, "doesnot exist", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(NavigationDrawer.this, "ERROR : 404", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dashboard()).commit();
                break;
            case R.id.nav_instructions:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InstructionsCard()).commit();
                break;
            case R.id.nav_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminDashboard()).commit();
                break;
            case R.id.nav_trainer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Trainer()).commit();
                break;
            case R.id.nav_client:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Client()).commit();
                break;
            case R.id.nav_map:
                Intent intent6 = new Intent(getApplicationContext(), Map.class);
                startActivity(intent6);
                finish();
                break;
            case R.id.nav_login:
                Intent intent7 = new Intent(NavigationDrawer.this, Login.class);
                startActivity(intent7);
                finish();
                break;
            case R.id.nav_update:
                Intent intent8 = new Intent(NavigationDrawer.this, UserProfile.class);
                startActivity(intent8);
                finish();
                break;
            case R.id.nav_logout:
                logout(this);
                break;
            case R.id.nav_aboutus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactusCard()).commit();
                break;
            case R.id.nav_rate:
                Intent intent9 = new Intent(NavigationDrawer.this, Rate.class);
                startActivity(intent9);
                finish();
                break;

            case R.id.rate_dashh:
                Intent intent10 = new Intent(NavigationDrawer.this, RateDash.class);
                startActivity(intent10);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
}