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
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Client extends AppCompatActivity {

    private long backPressedTime;
    DrawerLayout drawerLayout;
    RecyclerView sch_recev;
    myadapterSchedule adapter;

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
        setContentView(R.layout.activity_client);
        drawerLayout = findViewById(R.id.drawer_layout);

        sch_recev = findViewById(R.id.sch_recev);

        sch_recev = (RecyclerView) findViewById(R.id.sch_recev);
        sch_recev.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserHelperClassSchedule> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClassSchedule>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("schedule"), UserHelperClassSchedule.class)
                        .build();

        adapter = new myadapterSchedule(options);
        sch_recev.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}