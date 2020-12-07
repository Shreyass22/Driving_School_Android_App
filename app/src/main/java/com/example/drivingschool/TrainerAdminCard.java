package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TrainerAdminCard extends Fragment {

    private long backPressedTime;
    DrawerLayout drawerLayout;
    FloatingActionButton f_add_trainer;
    RecyclerView recview_trainer;
    myadapter adapter;

    List<DatabaseReference> archivedTr = new ArrayList<DatabaseReference>(); //archived

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
        View rootView = inflater.inflate(R.layout.activity_trainer_admin_card, container, false);

        drawerLayout = rootView.findViewById(R.id.drawer_layout);



//        f_add_trainer = (FloatingActionButton) findViewById(R.id.f_add_trainer);
//        f_add_trainer.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),TrainerAdProfile.class)));

        recview_trainer = (RecyclerView) rootView.findViewById(R.id.recview_trainer);
        recview_trainer.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<UserHelperClass> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), UserHelperClass.class)
                        .build();

        adapter = new myadapter(options);
        recview_trainer.setAdapter(adapter);

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
                        adapter.delTr(pos);
                        adapter.notifyItemRemoved(pos);
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
        }).attachToRecyclerView(recview_trainer);

        return rootView;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trainer_admin_card);
//    }

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