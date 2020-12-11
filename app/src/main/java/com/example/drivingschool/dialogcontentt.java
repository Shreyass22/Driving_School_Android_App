package com.example.drivingschool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class dialogcontentt extends AppCompatActivity {

    TextView presentvalue;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogcontentt);

        presentvalue = (TextView) findViewById(R.id.presentvalue);
    }


    public void present(View view) {
        count++;
        presentvalue.setText("" + count);

        if (count == 30) {

        }
    }

    public void save(View view) {
    }
}