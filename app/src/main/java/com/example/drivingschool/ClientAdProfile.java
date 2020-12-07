package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class ClientAdProfile extends Fragment {
    private static final Pattern Password_Pattern =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private long backPressedTime;
    private DrawerLayout drawerLayout;
    private TextInputLayout cl_name, cl_email, cl_phone, cl_password;
    private RadioGroup gender_radio_btn;
    private ProgressBar add_progess_bar;
    private Button addDataCl;

    private FirebaseDatabase rootNode;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth2;
    private DatabaseReference reference;

//    @Override
//    public void onBackPressed() {
//        if (backPressedTime + 3000 > System.currentTimeMillis()){
//            super.onBackPressed();
////            System.exit(0);
//            return;
//        }
//        else {
//            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        backPressedTime = System.currentTimeMillis();
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_client_ad_profile, container, false);

        drawerLayout = rootView.findViewById(R.id.drawer_layout);



        cl_name = rootView.findViewById(R.id.cl_name);
        cl_email = rootView.findViewById(R.id.cl_email);
        cl_phone = rootView.findViewById(R.id.cl_phone);
        cl_password = rootView.findViewById(R.id.cl_password);
        gender_radio_btn = rootView.findViewById(R.id.gender_radio_btn);
        add_progess_bar = rootView.findViewById(R.id.add_progess_bar);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://driving-school-bbc95.firebaseio.com/")             //[Database_url_here]
                .setApiKey("AIzaSyANIAdCPJ_ycRg08blcu24u4uoteP3_SFs")                       //Web_API_KEY_HERE
                .setApplicationId("driving-school-bbc95").build();                          //PROJECT_ID_HERE

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getContext(), firebaseOptions, "Driving School");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("Driving School"));
        }

        addDataCl = rootView.findViewById(R.id.add_btn33);
        addDataCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = cl_name.getEditText().getText().toString();
                String email = cl_email.getEditText().getText().toString();
                String phone = cl_phone.getEditText().getText().toString();
                String password = cl_password.getEditText().getText().toString();
                int checkId = gender_radio_btn.getCheckedRadioButtonId();
                RadioButton selected_gender = gender_radio_btn.findViewById(checkId);
                if (selected_gender == null) {
                    Toast.makeText(getContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                }
                else {
                    final String gender = selected_gender.getText().toString();
                    //validate
                    if(!validateName() | !validateEmail() | !validatePhone() | !validatePassword()) {
                        return;
                    }
                    else{
                        processinsert(name, email, phone, password, gender);
                        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            myMessage();
                        }
                        else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            //ActivityCompat.requestPermissions(getContext(), new String[] {Manifest.permission.SEND_SMS}, 0);
                        }
                    }
                }
            }
        });

        return rootView;
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_client_ad_profile);
//    }

    private Boolean validateName () {
        String val = cl_name.getEditText().getText().toString();
        if(val.isEmpty()) {
            cl_name.setError("Field cannot be empty");
            return false;
        }
        else{
            cl_name.setError(null);
            cl_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail () {
        String val = cl_email.getEditText().getText().toString();

        if(val.isEmpty()) {
            cl_email.setError("Field cannot be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            cl_email.setError("Enter valid E-mail");
            return false;
        }
        else{
            cl_email.setError(null);
            cl_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone () {
        String val = cl_phone.getEditText().getText().toString();
        if(val.isEmpty()) {
            cl_phone.setError("Field cannot be empty");
            return false;
        }
        else{
            cl_phone.setError(null);
            cl_phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword () {
        String val = cl_password.getEditText().getText().toString();
        if(val.isEmpty()) {
            cl_password.setError("Field cannot be empty");
            return false;
        }
        else if (!Password_Pattern.matcher(val).matches()) {
            cl_password.setError("Password is too weak");
            return false;
        }
        else{
            cl_password.setError(null);
            cl_password.setErrorEnabled(false);
            return true;
        }
    }

    //save data in firebase on button click
//    public void addDataCl(View view) {
//    }

    private void processinsert(String name, String email, String phone, String password, String gender) {
        add_progess_bar.setVisibility(View.VISIBLE);
        mAuth2.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                FirebaseUser rUser = mAuth2.getCurrentUser();
                String userID = rUser.getUid();
                reference = rootNode.getReference("users").child(userID); //realtimedb
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userId", userID);
                hashMap.put("name", name);
                hashMap.put("email", email);
                hashMap.put("phone", phone);
                hashMap.put("password", password);
                hashMap.put("gender", gender);
                // specify if the user is client
                hashMap.put("type", "Client");


                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    add_progess_bar.setVisibility(View.GONE);
                    if (task1.isSuccessful()) {
//                        Toast.makeText(getContext(), "Client Registration Successful", Toast.LENGTH_SHORT).show();
//                        Fragment fm = new Fragment();
//                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                        ft.replace(R.id.drawer_layout, new ClientAdminCard()).commit();
                        mAuth2.signOut();
                        //startActivity(intent);
                        //finish();
                    }
                    else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                add_progess_bar.setVisibility(View.GONE);
                Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void myMessage() {
        String name1 = cl_name.getEditText().getText().toString();
        String email1 = cl_email.getEditText().getText().toString();
        String phone1 = cl_phone.getEditText().getText().toString();
        String password1 = cl_password.getEditText().getText().toString();
        String msg1 = "Greetings from Driving School!\n This message is related to the credentials for Login in our app.\n Name:-" + name1 +"\n" +
                "Mailid:-" + email1 + "\n" + "Password:-" + password1 + "\n" +
                "Please don't share this credentials with anyone\n Thank You,\n Regards,\n Driving School.";

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone1, null, msg1, null, null);
        Toast.makeText(getContext(), "Message Send", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myMessage();
                }
                else {
                    Toast.makeText(getContext(), "You don't have Required Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}