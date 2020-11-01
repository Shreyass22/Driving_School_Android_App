package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
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


}
