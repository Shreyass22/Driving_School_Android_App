package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.hsalf.smilerating.SmileRating;

public class Rate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {

                switch (smiley) {
                    case SmileRating.BAD:
                        Toast.makeText(Rate.this, "BAD", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        Toast.makeText(Rate.this, "GOOD", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        Toast.makeText(Rate.this, "GREAT", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        Toast.makeText(Rate.this, "OKAY", Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.TERRIBLE:
                        Toast.makeText(Rate.this, "TERRIBLE", Toast.LENGTH_SHORT).show();
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
    }
}