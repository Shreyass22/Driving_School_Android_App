package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddSchedule extends AppCompatActivity {


    DrawerLayout drawerLayout;
    TextInputLayout sch_c_name, sch_t_name, sch_cr_name, sch_d_name, sch_ti_name;
    ProgressBar add_progess_bar;

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        drawerLayout = findViewById(R.id.drawer_layout);


        sch_c_name = findViewById(R.id.sch_c_name);
        sch_t_name = findViewById(R.id.sch_t_name);
        sch_cr_name = findViewById(R.id.sch_cr_name);
        sch_d_name = findViewById(R.id.sch_d_name);
        sch_ti_name = findViewById(R.id.sch_ti_name);
        add_progess_bar = findViewById(R.id.add_progess_bar);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

    }

    //save data in firebase on button click
    public void addSch(View view) {

        String c_name = sch_c_name.getEditText().getText().toString();
        String t_name = sch_t_name.getEditText().getText().toString();
        String car_name = sch_cr_name.getEditText().getText().toString();
        String datee = sch_d_name.getEditText().getText().toString();
        String timee = sch_ti_name.getEditText().getText().toString();

        processinsertsch(c_name, t_name, car_name, datee, timee);
    }

    private void processinsertsch(String c_name, String t_name, String car_name, String datee, String timee) {
        add_progess_bar.setVisibility(View.VISIBLE);
        reference = rootNode.getReference("schedule").child(c_name); //realtimedb
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name", c_name);
        hashMap.put("trainer", t_name);
        hashMap.put("car", car_name);
        hashMap.put("date", datee);
        hashMap.put("time", timee);
        // Car add
        hashMap.put("type", "schedule");

        reference.setValue(hashMap).addOnCompleteListener(task1 -> {
            add_progess_bar.setVisibility(View.GONE);
            if (task1.isSuccessful()) {
                Intent intent = new Intent(AddSchedule.this,AdminDashboard.class);
                Toast.makeText(AddSchedule.this, "Schdule Added", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
            else {
                Toast.makeText(AddSchedule.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}