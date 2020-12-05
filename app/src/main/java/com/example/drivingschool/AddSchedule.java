package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AddSchedule extends AppCompatActivity {


    DrawerLayout drawerLayout;
    //TextInputLayout sch_c_name, sch_t_name, sch_cr_name, sch_d_name, sch_ti_name;
    ProgressBar add_progess_bar;

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

    TextView spinnerDate;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Spinner spinnerCl, spinnerTr, spinnerCr, spinnerTime;
    DatabaseReference databaseReferenceCl;
    ValueEventListener listenerCl;
    ArrayAdapter<String> adapterCl, adapterTr, adapterCr, adapterTime;
    ArrayList<String> spinnerDtCl;
    private UserHelperClass usersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        drawerLayout = findViewById(R.id.drawer_layout);


//        sch_c_name = findViewById(R.id.sch_c_name);
//        sch_t_name = findViewById(R.id.sch_t_name);
//        sch_cr_name = findViewById(R.id.sch_cr_name);
//        sch_d_name = findViewById(R.id.sch_d_name);
//        sch_ti_name = findViewById(R.id.sch_ti_name);
        add_progess_bar = findViewById(R.id.add_progess_bar);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReferenceCl = FirebaseDatabase.getInstance().getReference("users");
        spinnerCl = (Spinner) findViewById(R.id.mySpinnerCl);
        spinnerTr = (Spinner) findViewById(R.id.mySpinnerTr);
        spinnerCr = (Spinner) findViewById(R.id.mySpinnerCr);
        spinnerTime = (Spinner) findViewById(R.id.mySpinnerTime);


        spinnerDate = findViewById(R.id.mySpinnerDate);
        spinnerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddSchedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" + month + "/" + year;
                spinnerDate.setText(date);
            }
        };

        //spinnerDtCl = new ArrayList<>();
        //client
        adapterCl = new ArrayAdapter<>(AddSchedule.this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.Clnames));
        adapterCl.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerCl.setAdapter(adapterCl);
        //retriveCl();

        //trainer
        adapterTr = new ArrayAdapter<>(AddSchedule.this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.Tnames));
        adapterTr.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerTr.setAdapter(adapterTr);

        //car
        adapterCr = new ArrayAdapter<>(AddSchedule.this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.Crnames));
        adapterCr.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerCr.setAdapter(adapterCr);

        //date
        adapterTime = new ArrayAdapter<>(AddSchedule.this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.Time));
        adapterTime.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerTime.setAdapter(adapterTime);


    }

    //spinnner retrive data
//    public void retriveCl() {
//        listenerCl = databaseReferenceCl.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot item : snapshot.getChildren()) {
//                    spinnerDtCl.add(item.getValue().toString());
//                }
//                adapterCl.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    //save data in firebase on button click
    public void addSch(View view) {

        String c_name = spinnerCl.getSelectedItem().toString();
        String t_name = spinnerTr.getSelectedItem().toString();
        String car_name = spinnerCr.getSelectedItem().toString();
        String timee = spinnerTime.getSelectedItem().toString();
        String datee = spinnerDate.getText().toString();

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