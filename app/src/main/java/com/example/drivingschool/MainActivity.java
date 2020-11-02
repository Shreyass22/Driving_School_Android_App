package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.drivingschool.Dashboard.closeDrawer;

public class MainActivity extends AppCompatActivity {

    //variables
    Animation top_animation, botton_animation;
    ImageView image;
    TextView text, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //animation
        Animation top_animation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation botton_animation = AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        //Hooks
        image = findViewById(R.id.simpleImageView);
        text = findViewById(R.id.textView);
        text2 = findViewById(R.id.textView2);

        //Set animation to elements
        image.setAnimation(top_animation);
        text.setAnimation(botton_animation);
        text2.setAnimation(botton_animation);


        int SPLASH_SCREEN = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(text,"logo_text");
                pairs[2] = new Pair<View,String>(text2,"logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent,options.toBundle());
            }
        }, SPLASH_SCREEN);
    }
}