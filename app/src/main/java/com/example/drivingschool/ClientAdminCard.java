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

public class ClientAdminCard extends AppCompatActivity {

    String[] spaceProbeHeadersClient ={"ID", "Name", "Sessions"};
    String [][] spaceProbesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_admin_card);

        //Client list start--------------------------------------------------------------
        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.client_list);
        tb.setColumnCount(3);
        tb.setHeaderBackgroundColor(Color.parseColor("#2286ff"));

        //  Populate
        populateData();
        //Adapters
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeadersClient));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbesClient));

        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Toast.makeText(ClientAdminCard.this, ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();

            }
        });
        //Client list stops--------------------------------------------------------------

    }

    //Client list start--------------------------------------------------------------
    private void populateData()
    {
        SpaceprobeClient spaceprobeclient =new SpaceprobeClient();
        ArrayList<SpaceprobeClient> spaceprobeclientList=new ArrayList<>();


        spaceprobeclient.setCl_id("1");
        spaceprobeclient.setCl_name("Pioneer");
        spaceprobeclient.setCl_session("4");
        spaceprobeclientList.add(spaceprobeclient);

        spaceprobeclient=new SpaceprobeClient();
        spaceprobeclient.setCl_id("2");
        spaceprobeclient.setCl_name("Casini");
        spaceprobeclient.setCl_session("5");
        spaceprobeclientList.add(spaceprobeclient);

        spaceprobeclient=new SpaceprobeClient();
        spaceprobeclient.setCl_id("3");
        spaceprobeclient.setCl_name("Apollo");
        spaceprobeclient.setCl_session("2");
        spaceprobeclientList.add(spaceprobeclient);

        spaceprobeclient=new SpaceprobeClient();
        spaceprobeclient.setCl_id("4");
        spaceprobeclient.setCl_name("Enterpise");
        spaceprobeclient.setCl_session("3");
        spaceprobeclientList.add(spaceprobeclient);

        spaceProbesClient = new String[spaceprobeclientList.size()][3];
        // spaceProbes= new String[][]{{}};


        for (int i=0 ;i< spaceprobeclientList.size();i++) {

            SpaceprobeClient s2 =spaceprobeclientList.get(i);

            spaceProbesClient[i][0]=s2.getCl_id();
            spaceProbesClient[i][1]=s2.getCl_name();
            spaceProbesClient[i][2]=s2.getCl_session();
        }
    }
    //Client list stop--------------------------------------------------------------
}