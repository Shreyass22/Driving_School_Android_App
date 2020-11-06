package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TrainerAdminCard extends AppCompatActivity {

    String[] spaceProbeHeaders ={"ID", "Name", "Rating"};
    String [][] spaceProbes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_admin_card);

        //Trainer list start--------------------------------------------------------------
        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.trainer_list);
        tb.setColumnCount(3);
        tb.setHeaderBackgroundColor(Color.parseColor("#2286ff"));

        //  Populate
        populateData();
        //Adapters
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbes));

        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Toast.makeText(TrainerAdminCard.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();

            }
        });
        //Trainer list stops--------------------------------------------------------------
    }

    //Trainer list start--------------------------------------------------------------
    private void populateData()
    {
        Spaceprobe spaceprobe=new Spaceprobe();
        ArrayList<Spaceprobe> spaceprobeList=new ArrayList<>();


        spaceprobe.setTr_id("1");
        spaceprobe.setTr_name("Pioneer");
        spaceprobe.setTr_rating("4");
        spaceprobeList.add(spaceprobe);

        spaceprobe=new Spaceprobe();
        spaceprobe.setTr_id("2");
        spaceprobe.setTr_name("Casini");
        spaceprobe.setTr_rating("5");
        spaceprobeList.add(spaceprobe);

        spaceprobe=new Spaceprobe();
        spaceprobe.setTr_id("3");
        spaceprobe.setTr_name("Apollo");
        spaceprobe.setTr_rating("2");
        spaceprobeList.add(spaceprobe);

        spaceprobe=new Spaceprobe();
        spaceprobe.setTr_id("4");
        spaceprobe.setTr_name("Enterpise");
        spaceprobe.setTr_rating("3");
        spaceprobeList.add(spaceprobe);

        spaceProbes= new String[spaceprobeList.size()][3];
        // spaceProbes= new String[][]{{}};


        for (int i=0 ;i< spaceprobeList.size();i++) {

            Spaceprobe s=spaceprobeList.get(i);

            spaceProbes[i][0]=s.getTr_id();
            spaceProbes[i][1]=s.getTr_name();
            spaceProbes[i][2]=s.getTr_rating();
        }
    }
    //Trainer list stop--------------------------------------------------------------

}