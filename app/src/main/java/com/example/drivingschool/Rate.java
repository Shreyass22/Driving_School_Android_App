package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;

import java.util.HashMap;
import java.util.Objects;

public class Rate extends AppCompatActivity {

    TextView feedback_name, feedback_mail, feedback_type;
    EditText feedback_msg;
    private long backPressedTime;
    DrawerLayout drawerLayout;
    Button submit_feedback;
    ProgressBar progess_barrr;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference databaseReference;
    private UserHelperClass usersData;

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
        setContentView(R.layout.activity_rate);
        drawerLayout = findViewById(R.id.drawer_layout);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        feedback_name = findViewById(R.id.feedback_name);
        feedback_mail = findViewById(R.id.feedback_mail);
        feedback_msg = findViewById(R.id.feedback_msg);
        feedback_type = findViewById(R.id.feedback_type);
        submit_feedback = findViewById(R.id.submit_feedback);
        progess_barrr = findViewById(R.id.progess_barrr);

        //rate
        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {

                switch (smiley) {
                    case SmileRating.TERRIBLE:

                        Toast.makeText(Rate.this, "TERRIBLE", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.BAD:
                        Toast.makeText(Rate.this, "BAD", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        Toast.makeText(Rate.this, "OKAY", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        Toast.makeText(Rate.this, "GOOD", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        Toast.makeText(Rate.this, "GREAT", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                Toast.makeText(Rate.this, "Rating level "+ level, Toast.LENGTH_SHORT).show();

            }
        });
        //rate


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("client1", "onDataChange: " + snap2.toString());
//                if (snapshot.getKey().equals(firebaseUser.getUid())) {
                Log.d("client2", "HEllo" + snapshot.getValue().toString());
                usersData = snapshot.getValue(UserHelperClass.class);
                assert usersData != null;
                feedback_name.setText((usersData.getName()));
                feedback_mail.setText((usersData.getEmail()));
                feedback_type.setText((usersData.getType()));
//                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Rate.this, error.getMessage(), Toast.LENGTH_SHORT);
            }
        });

    }

    public void submit_feedback(View view) {
        String name = feedback_name.getText().toString();
        String mail = feedback_mail.getText().toString();
        String type = feedback_type.getText().toString();
        String msg = feedback_msg.getText().toString();

        processinsertFeed(name, mail, type, msg);

    }

    private void processinsertFeed(String name, String mail, String type, String msg) {
        progess_barrr.setVisibility(View.VISIBLE);
        databaseReference = rootNode.getReference("feedback").child(name); //realtimedb
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("mail", mail);
        hashMap.put("type", type);
        hashMap.put("message", msg);

        databaseReference.setValue(hashMap).addOnCompleteListener(task1 -> {
            progess_barrr.setVisibility(View.GONE);
            if (task1.isSuccessful()) {
                Toast.makeText(Rate.this, "Thanks for FeedBack", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Rate.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}