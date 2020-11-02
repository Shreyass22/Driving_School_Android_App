package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class AdminDashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

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
    }

    public void ClickLogo(View view){
        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickDashboard(View view){
        Dashboard.redirectActivity(this,Dashboard.class);
    }

    public void ClickInstructions(View view){
        Dashboard.redirectActivity(this,InstructionsCard.class);
    }

    public void ClickAdmin(View view){
        recreate();
    }

    public void ClickTrainer(View view){
        Dashboard.redirectActivity(this,Trainer.class);
    }

    public void ClickClient(View view){
        Dashboard.redirectActivity(this,Client.class);
    }

    public void ClickLogin(View view){
        Dashboard.redirectActivity(this,Login.class);
    }

    public void ClickUpdate(View view){
        Dashboard.redirectActivity(this,UserProfile.class);
    }

    public void ClickAboutus(View view){
        Dashboard.redirectActivity(this,ContactusCard.class);
    }

    public void ClickRate(View view){
        Dashboard.redirectActivity(this,Rate.class);
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
