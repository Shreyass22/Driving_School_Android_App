package com.example.drivingschool;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class dialodcontent extends AppCompatActivity {

    TextView presentvalue;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialodcontent);

        presentvalue = (TextView) findViewById(R.id.presentvalue);
    }


    public void present(View view) {
        count++;
        presentvalue.setText("" + count);
    }

    public void save(View view) {
    }
}
