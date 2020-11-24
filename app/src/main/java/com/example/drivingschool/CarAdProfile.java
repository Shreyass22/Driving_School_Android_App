package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class CarAdProfile extends AppCompatActivity {

    private long backPressedTime;

    DrawerLayout drawerLayout;
    TextInputLayout cr_name, cr_model, cr_company, cr_color;
    ProgressBar add_progess_bar;

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()){
            super.onBackPressed();
//            System.exit(0);
            return;
        }
        else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_ad_profile);
        drawerLayout = findViewById(R.id.drawer_layout);




        cr_name = findViewById(R.id.cr_name);
        cr_model = findViewById(R.id.cr_model);
        cr_company = findViewById(R.id.cr_company);
        cr_color = findViewById(R.id.cr_color);
        add_progess_bar = findViewById(R.id.add_progess_bar);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private Boolean validateName () {
        String val = cr_name.getEditText().getText().toString();
        if(val.isEmpty()) {
            cr_name.setError("Field cannot be empty");
            return false;
        }
        else{
            cr_name.setError(null);
            cr_name.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateModel () {
        String val = cr_model.getEditText().getText().toString();
        if(val.isEmpty()) {
            cr_model.setError("Field cannot be empty");
            return false;
        }
        else{
            cr_model.setError(null);
            cr_model.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateCompany () {
        String val = cr_company.getEditText().getText().toString();
        if(val.isEmpty()) {
            cr_company.setError("Field cannot be empty");
            return false;
        }
        else{
            cr_company.setError(null);
            cr_company.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateColor () {
        String val = cr_color.getEditText().getText().toString();
        if(val.isEmpty()) {
            cr_color.setError("Field cannot be empty");
            return false;
        }
        else{
            cr_color.setError(null);
            cr_color.setErrorEnabled(false);
            return true;
        }
    }

    //save data in firebase on button click
    public void addDataCar(View view) {

        String name = cr_name.getEditText().getText().toString();
        String model = cr_model.getEditText().getText().toString();
        String company = cr_company.getEditText().getText().toString();
        String color = cr_color.getEditText().getText().toString();

        //validate
        if(!validateName() | !validateModel() | !validateCompany() | !validateColor()) {
            return;
        }
        else{
            processinsertCar(name, model, company, color);
        }
    }

    private void processinsertCar(String name, String model, String company, String color) {
        add_progess_bar.setVisibility(View.VISIBLE);
        reference = rootNode.getReference("car").child(name); //realtimedb
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("model", model);
        hashMap.put("company", company);
        hashMap.put("color", color);
        // Car add
        hashMap.put("isCar", "1");

        reference.setValue(hashMap).addOnCompleteListener(task1 -> {
            add_progess_bar.setVisibility(View.GONE);
            if (task1.isSuccessful()) {
                Intent intent = new Intent(CarAdProfile.this,CarAdminCard.class);
                Toast.makeText(CarAdProfile.this, "Car Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(CarAdProfile.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //navigation drawer starts
    public void ClickMenu(View view) {
        Dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickDashboard(View view) {
        Dashboard.redirectActivity(this, Dashboard.class);
        this.finish();
    }

    public void ClickInstructions(View view) {
        Dashboard.redirectActivity(this, InstructionsCard.class);
        this.finish();
    }

    public void ClickAdmin(View view) {
        Dashboard.redirectActivity(this, AdminDashboard.class);
        this.finish();
    }

    public void ClickTrainer(View view) {
        Dashboard.redirectActivity(this, Trainer.class);
        this.finish();
    }

    public void ClickClient(View view) {
        Dashboard.redirectActivity(this, Client.class);
        this.finish();
    }

    public void ClickLogin(View view) {
        Dashboard.redirectActivity(this, Login.class);
        this.finish();
    }

    public void ClickUpdate(View view) {
        Dashboard.redirectActivity(this, UserProfile.class);
        this.finish();
    }

    public void ClickAboutus(View view) {
        Dashboard.redirectActivity(this, ContactusCard.class);
        this.finish();
    }

    public void ClickRate(View view) {
        Dashboard.redirectActivity(this, Rate.class);
        this.finish();
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public void logout(final Activity activity){
        android.app.AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(((Dialog) dialog).getContext(), Login.class);
                startActivity(myIntent);
                return;
            }
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    protected void onPause() {
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    //navigation drawer ends
}