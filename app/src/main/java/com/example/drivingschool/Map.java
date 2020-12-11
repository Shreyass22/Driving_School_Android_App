package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Map extends AppCompatActivity {

    private long backPressedTime;;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;


    //on back press app exit
    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()){
            //super.onBackPressed();
//            System.exit(0);
            startActivity(new Intent(Map.this, NavigationDrawer.class));
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
        setContentView(R.layout.activity_map);
    }

    public void map_loc(View view) {
        startActivity(new Intent(Map.this, MapsActivity.class));
    }

    public void retrive_loc(View view) {
        startActivity(new Intent(Map.this, RetriveMapsActivity.class));
    }

}



