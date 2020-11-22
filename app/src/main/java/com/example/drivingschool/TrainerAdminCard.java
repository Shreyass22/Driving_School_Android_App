package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TrainerAdminCard extends AppCompatActivity {

    FloatingActionButton f_add_trainer;
    RecyclerView recview_trainer;
    myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_admin_card);

        f_add_trainer = (FloatingActionButton) findViewById(R.id.f_add_trainer);
        f_add_trainer.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),TrainerAdProfile.class)));

        recview_trainer = (RecyclerView) findViewById(R.id.recview_trainer);
        recview_trainer.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserHelperClass> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("trainer"), UserHelperClass.class)
                        .build();

        adapter = new myadapter(options);
        recview_trainer.setAdapter(adapter);

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