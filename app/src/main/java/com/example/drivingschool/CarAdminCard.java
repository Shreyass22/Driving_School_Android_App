package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class CarAdminCard extends AppCompatActivity {

    private long backPressedTime;
    DrawerLayout drawerLayout;
    FloatingActionButton f_add_car;
    RecyclerView recview_car;
    myadapterCar adapterCar;

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
        setContentView(R.layout.activity_car_admin_card);
        drawerLayout = findViewById(R.id.drawer_layout);




        f_add_car = (FloatingActionButton) findViewById(R.id.f_add_car);
        f_add_car.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),CarAdProfile.class)));

        recview_car = (RecyclerView) findViewById(R.id.recview_car);
        recview_car.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserHelperClassCar> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClassCar>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("car"), UserHelperClassCar.class)
                        .build();

        adapterCar = new myadapterCar(options);
        recview_car.setAdapter(adapterCar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterCar.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterCar.stopListening();
    }

    //navigation drawer starts
    public void ClickMenu(View view) {
        Dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickDashboard(View view) {
        Dashboard.redirectActivity(this, Dashboard.class);
        this.finish();
    }

    public void ClickInstructions(View view) {
        Dashboard.redirectActivity(this, InstructionsCard.class);
        this.finish();
    }

    public void ClickAdmin(View view) {
        Dashboard.redirectActivity(this, AdminDashboard.class);
        this.finish();
    }

    public void ClickTrainer(View view) {
        Dashboard.redirectActivity(this, Trainer.class);
        this.finish();
    }

    public void ClickClient(View view) {
        Dashboard.redirectActivity(this, Client.class);
        this.finish();
    }

    public void ClickLogin(View view) {
        Dashboard.redirectActivity(this, Login.class);
        this.finish();
    }

    public void ClickUpdate(View view) {
        Dashboard.redirectActivity(this, UserProfile.class);
        this.finish();
    }

    public void ClickAboutus(View view) {
        Dashboard.redirectActivity(this, ContactusCard.class);
        this.finish();
    }

    public void ClickRate(View view) {
        Dashboard.redirectActivity(this, Rate.class);
        this.finish();
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public void logout(final Activity activity){
        android.app.AlertDialog.Builder builder= new AlertDialog.Builder(activity);
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

    protected void onPause() {
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    //navigation drawer ends

}