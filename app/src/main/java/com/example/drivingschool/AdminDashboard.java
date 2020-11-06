package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawerLayout;
    private CardView trainer_card, car_card, client_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        drawerLayout = findViewById(R.id.drawer_layout);

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

    //table
    private boolean table_flg = false;
    public void collapseTable(View view) {
        TableLayout table = findViewById(R.id.table);
        Button switchBtn = findViewById(R.id.switchBtn);

        // setColumnCollapsed(int columnIndex, boolean isCollapsed)
        table.setColumnCollapsed(1, table_flg);
        table.setColumnCollapsed(2, table_flg);
        table.setColumnCollapsed(3, table_flg);
        table.setColumnCollapsed(4, table_flg);

        if (table_flg) {
            // Close
            table_flg = false;
            switchBtn.setText("Show Detail");
        } else {
            // Open
            table_flg = true;
            switchBtn.setText("Hide Detail");
        }

    }
    //table end

    //navigation drawer starts
    public void ClickMenu(View view){
        Dashboard.openDrawer(drawerLayout);
        this.finish();
    }

    public void ClickLogo(View view){
        Dashboard.closeDrawer(drawerLayout);
        this.finish();
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
        Dashboard.logout(this);
    }

    protected void onPause(){
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    //navigation drawer ends
}
