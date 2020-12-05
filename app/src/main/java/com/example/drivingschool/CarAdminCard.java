package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
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
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

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




//        f_add_car = (FloatingActionButton) findViewById(R.id.f_add_car);
//        f_add_car.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),CarAdProfile.class)));

        recview_car = (RecyclerView) findViewById(R.id.recview_car);
        recview_car.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserHelperClassCar> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClassCar>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("car"), UserHelperClassCar.class)
                        .build();

        adapterCar = new myadapterCar(options);
        recview_car.setAdapter(adapterCar);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();

                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        Toast.makeText(CarAdminCard.this, "Deleted", Toast.LENGTH_SHORT).show();
                        adapterCar.delCr(pos);
                        adapterCar.notifyItemRemoved(pos);
                        break;

                    //archived
                    case ItemTouchHelper.RIGHT:
                        Toast.makeText(CarAdminCard.this, "Archived", Toast.LENGTH_SHORT).show();
//                        final DatabaseReference trName = adapter.getRef(pos);
//                        archivedTr.add(trName);
//
//                        adapter.
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(CarAdminCard.this, R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.icon_delete)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(CarAdminCard.this,R.color.green))
                        .addSwipeRightActionIcon(R.drawable.icon_archive)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recview_car);

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


}