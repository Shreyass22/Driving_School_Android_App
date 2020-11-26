package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener {
    private long backPressedTime;
    DrawerLayout drawerLayout;
    private CardView trainer_card, car_card, client_card;
    AlertDialog.Builder builder;
    Context context;



    RecyclerView sch_recev;
    ArrayList<dataUser> list = new ArrayList<>();
    AdapterItem adapterItem;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

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

//        Spinner myspinner = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(AdminDashboard.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
//        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        myspinner.setAdapter(myadapter);
//
//        Spinner myspinner1 = (Spinner) findViewById(R.id.spinner1);
//        ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(AdminDashboard.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Cnames));
//        myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        myspinner1.setAdapter(myadapter);

        context = this;

        builder = new AlertDialog.Builder(context);

        FloatingActionButton f_add_schedule = findViewById(R.id.f_add_schedule);
        f_add_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
                //startActivity(new Intent(getApplicationContext(), AddSchedule.class));
            }
        });
        FloatingActionButton f_add_trainer = findViewById(R.id.f_add_trainer);
        f_add_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TrainerAdProfile.class));
            }
        });
        FloatingActionButton f_add_client = findViewById(R.id.f_add_client);
        f_add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClientAdProfile.class));
            }
        });
        FloatingActionButton f_add_car = findViewById(R.id.f_add_car);
        f_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CarAdProfile.class));
            }
        });



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

    private void inputData() {

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



    //navigation drawer starts
    public void ClickMenu(View view){
        Dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickDashboard(View view){
        Dashboard.redirectActivity(this,Dashboard.class);
        this.finish();
    }

    public void ClickInstructions(View view){
        Dashboard.redirectActivity(this,InstructionsCard.class);
        this.finish();
    }

    public void ClickAdmin(View view){
        recreate();
    }

    public void ClickTrainer(View view){
        Dashboard.redirectActivity(this,Trainer.class);
        this.finish();
    }

    public void ClickClient(View view){
        Dashboard.redirectActivity(this,Client.class);
        this.finish();
    }

    public void ClickLogin(View view){
        Dashboard.redirectActivity(this,Login.class);
        this.finish();
    }

    public void ClickUpdate(View view){
        Dashboard.redirectActivity(this,UserProfile.class);
        this.finish();
    }

    public void ClickAboutus(View view){
        Dashboard.redirectActivity(this,ContactusCard.class);
        this.finish();
    }

    public void ClickRate(View view){
        Dashboard.redirectActivity(this,Rate.class);
        this.finish();
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public void logout(final Activity activity){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(((Dialog) dialog).getContext(), Login.class);
                startActivity(myIntent);
                return;
            }
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    protected void onPause(){
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    //navigation drawer ends
}
