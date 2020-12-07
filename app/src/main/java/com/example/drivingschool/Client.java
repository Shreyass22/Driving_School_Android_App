package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Client extends Fragment {

    private long backPressedTime;
    DrawerLayout drawerLayout;
    RecyclerView sch_recev;
    myadapterSchedule adapter;

//    @Override
//    public void onBackPressed() {
//        if (backPressedTime + 3000 > System.currentTimeMillis()){
//            super.onBackPressed();
////            System.exit(0);
//            return;
//        }
//        else {
//            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        backPressedTime = System.currentTimeMillis();
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_client, container, false);

        drawerLayout = rootView.findViewById(R.id.drawer_layout);

        sch_recev = rootView.findViewById(R.id.sch_recev);

        sch_recev = (RecyclerView) rootView.findViewById(R.id.sch_recev);
        sch_recev.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<UserHelperClassSchedule> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClassSchedule>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("schedule"), UserHelperClassSchedule.class)
                        .build();

        adapter = new myadapterSchedule(options);
        sch_recev.setAdapter(adapter);




        return rootView;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_client);
//        }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}