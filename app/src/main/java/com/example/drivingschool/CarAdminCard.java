package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class CarAdminCard extends AppCompatActivity {

    String[] spaceProbeHeadersCar ={"ID", "Name", "Rating"};
    String [][] spaceProbesCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_admin_card);

        //Car list start--------------------------------------------------------------
        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.car_list);
        tb.setColumnCount(3);
        tb.setHeaderBackgroundColor(Color.parseColor("#2286ff"));

        //  Populate
        populateData();
        //Adapters
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeadersCar));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbesCar));

        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Toast.makeText(CarAdminCard.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();

            }
        });
        //Car list stops--------------------------------------------------------------
    }

    //Trainer list start--------------------------------------------------------------
    private void populateData()
    {
        SpaceprobeCar spaceprobecar =new SpaceprobeCar();
        ArrayList<SpaceprobeCar> spaceprobecarList=new ArrayList<>();


        spaceprobecar.setCr_id("1");
        spaceprobecar.setCr_name("Pioneer");
        spaceprobecar.setCr_rating("4");
        spaceprobecarList.add(spaceprobecar);

        spaceprobecar=new SpaceprobeCar();
        spaceprobecar.setCr_id("2");
        spaceprobecar.setCr_name("Casini");
        spaceprobecar.setCr_rating("5");
        spaceprobecarList.add(spaceprobecar);

        spaceprobecar=new SpaceprobeCar();
        spaceprobecar.setCr_id("3");
        spaceprobecar.setCr_name("Apollo");
        spaceprobecar.setCr_rating("2");
        spaceprobecarList.add(spaceprobecar);

        spaceprobecar=new SpaceprobeCar();
        spaceprobecar.setCr_id("4");
        spaceprobecar.setCr_name("Enterpise");
        spaceprobecar.setCr_rating("3");
        spaceprobecarList.add(spaceprobecar);

        spaceProbesCar= new String[spaceprobecarList.size()][3];
        // spaceProbes= new String[][]{{}};


        for (int i=0 ;i< spaceprobecarList.size();i++) {

            SpaceprobeCar s1=spaceprobecarList.get(i);

            spaceProbesCar[i][0]=s1.getCr_id();
            spaceProbesCar[i][1]=s1.getCr_name();
            spaceProbesCar[i][2]=s1.getCr_rating();
        }
    }
    //Trainer list stop--------------------------------------------------------------

}