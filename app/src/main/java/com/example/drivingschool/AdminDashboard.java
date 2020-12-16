package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AdminDashboard extends Fragment implements View.OnClickListener {
    private long backPressedTime;
    DrawerLayout drawerLayout;
    private CardView trainer_card, car_card, client_card;
    myadapterScheduleC madapterC;
    RecyclerView sch_recev;

    //on back press app exit
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
        View rootView = inflater.inflate(R.layout.activity_admin_dashboard, container, false);

        drawerLayout = rootView.findViewById(R.id.drawer_layout);


        sch_recev = rootView.findViewById(R.id.sch_recev);

        sch_recev = (RecyclerView) rootView.findViewById(R.id.sch_recev);
        sch_recev.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<UserHelperClassSchedule> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClassSchedule>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("schedule"), UserHelperClassSchedule.class)
                        .build();

        madapterC = new myadapterScheduleC(options);
        sch_recev.setAdapter(madapterC);

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
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        madapterC.delSch(pos);
                        madapterC.notifyItemRemoved(pos);
                        break;

                    //archived
                    case ItemTouchHelper.RIGHT:
                        Toast.makeText(getContext(), "Archived", Toast.LENGTH_SHORT).show();
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
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.icon_delete)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.green))
                        .addSwipeRightActionIcon(R.drawable.icon_archive)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(sch_recev);


        FloatingActionButton f_add_schedule = rootView.findViewById(R.id.f_add_schedule);
        f_add_schedule.setOnClickListener(this);

        FloatingActionButton f_add_trainer = rootView.findViewById(R.id.f_add_trainer);
        f_add_trainer.setOnClickListener(this);

        FloatingActionButton f_add_client = rootView.findViewById(R.id.f_add_client);
        f_add_client.setOnClickListener(this);

        FloatingActionButton f_add_car = rootView.findViewById(R.id.f_add_car);
        f_add_car.setOnClickListener(this);



        //card on click listener starts-------------------------------------------------------------
        //hooks for cardview
        trainer_card = (CardView) rootView.findViewById(R.id.trainer_card);
        car_card = (CardView) rootView.findViewById(R.id.car_card);
        client_card = (CardView) rootView.findViewById(R.id.client_card);
        //Add Click listener to the cards
        trainer_card.setOnClickListener(this);
        car_card.setOnClickListener(this);
        client_card.setOnClickListener(this);
        //continue from line 123 of this file.
        //card on click listener stops--------------------------------------------------------------


        return rootView;
    }

    //floating button
//    public void onClick(View vi) {
//        Fragment fm = new Fragment();
//        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//        switch (vi.getId()) {
//
//            default: break;
//        }
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_dashboard);
//    }

    // onClick event on card
    public void onClick(View ve) {
        Fragment fm = new Fragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        switch (ve.getId()) {

            //admin cardsss
            case R.id.trainer_card : ft.replace(R.id.drawer_layout,
                    new TrainerAdminCard()).commit();
                break;
            case R.id.car_card : ft.replace(R.id.drawer_layout,
                    new CarAdminCard()).commit();
                break;
            case R.id.client_card : ft.replace(R.id.drawer_layout,
                    new ClientAdminCard()).commit();
                break;

                //admin cards end

            //floating cards
            case R.id.f_add_schedule : ft.replace(R.id.drawer_layout,
                    new AddSchedule()).commit();
                break;
            case R.id.f_add_trainer : ft.replace(R.id.drawer_layout,
                    new TrainerAdProfile()).commit();
                break;
            case R.id.f_add_client : ft.replace(R.id.drawer_layout,
                    new ClientAdProfile()).commit();
                break;
            case R.id.f_add_car : ft.replace(R.id.drawer_layout,
                    new CarAdProfile()).commit();
                break;
            //floating cards end
            default: break;
        }
    }
    // onClick event on card end


    @Override
    public void onStart() {
        super.onStart();
        madapterC.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        madapterC.stopListening();
    }
}
