package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RateDash extends AppCompatActivity {

    myRateadapter myRateadapter;
    RecyclerView recview_rate;
    private long backPressedTime;

    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            System.exit(0);
            startActivity(new Intent(RateDash.this, NavigationDrawer.class));
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_dash);


        recview_rate = findViewById(R.id.recview_rate);

        recview_rate = (RecyclerView) findViewById(R.id.recview_rate);
        recview_rate.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions<UserHelperClassRate> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClassRate>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("feedback"), UserHelperClassRate.class)
                        .build();

        myRateadapter = new myRateadapter(options);
        recview_rate.setAdapter(myRateadapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        myRateadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myRateadapter.stopListening();
    }
}