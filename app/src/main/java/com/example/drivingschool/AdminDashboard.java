package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener {
    private long backPressedTime;
    DrawerLayout drawerLayout;
    private CardView trainer_card, car_card, client_card;
    myadapterSchedule madapter;
    RecyclerView sch_recev;


    //on back press app exit
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
        setContentView(R.layout.activity_admin_dashboard);
        drawerLayout = findViewById(R.id.drawer_layout);

        sch_recev = findViewById(R.id.sch_recev);

        sch_recev = (RecyclerView) findViewById(R.id.sch_recev);
        sch_recev.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UserHelperClassSchedule> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClassSchedule>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("schedule"), UserHelperClassSchedule.class)
                        .build();

        madapter = new myadapterSchedule(options);
        sch_recev.setAdapter(madapter);

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
                        Toast.makeText(AdminDashboard.this, "Deleted", Toast.LENGTH_SHORT).show();
                        madapter.delSch(pos);
                        madapter.notifyItemRemoved(pos);
                        break;

                    //archived
                    case ItemTouchHelper.RIGHT:
                        Toast.makeText(AdminDashboard.this, "Archived", Toast.LENGTH_SHORT).show();
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
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(AdminDashboard.this, R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.icon_delete)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(AdminDashboard.this,R.color.green))
                        .addSwipeRightActionIcon(R.drawable.icon_archive)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(sch_recev);


        FloatingActionButton f_add_schedule = findViewById(R.id.f_add_schedule);
        f_add_schedule.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddSchedule.class)));

        FloatingActionButton f_add_trainer = findViewById(R.id.f_add_trainer);
        f_add_trainer.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TrainerAdProfile.class)));

        FloatingActionButton f_add_client = findViewById(R.id.f_add_client);
        f_add_client.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ClientAdProfile.class)));

        FloatingActionButton f_add_car = findViewById(R.id.f_add_car);
        f_add_car.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CarAdProfile.class)));



        //card on click listener starts-------------------------------------------------------------
        //hooks for cardview
        trainer_card = (CardView) findViewById(R.id.trainer_card);
        car_card = (CardView) findViewById(R.id.car_card);
        client_card = (CardView) findViewById(R.id.client_card);
        //Add Click listener to the cards
        trainer_card.setOnClickListener(this);
        car_card.setOnClickListener(this);
        client_card.setOnClickListener(this);
        //continue from line 123 of this file.
        //card on click listener stops--------------------------------------------------------------


    }


    // onClick event on card
    public void onClick(View ve) {
        Intent c, c1, c2, c3;
        switch (ve.getId()) {
            case R.id.trainer_card : c = new Intent(this, TrainerAdminCard.class); startActivity(c); break;
            case R.id.car_card : c1 = new Intent(this, CarAdminCard.class); startActivity(c1); break;
            case R.id.client_card : c2 = new Intent(this, ClientAdminCard.class); startActivity(c2); break;
            default: break;
        }
    }
    // onClick event on card end


    @Override
    protected void onStart() {
        super.onStart();
        madapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        madapter.stopListening();
    }
}
