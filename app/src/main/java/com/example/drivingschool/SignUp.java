package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private static final Pattern Password_Pattern =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    TextInputLayout reg_name, reg_email, reg_phone, reg_password;
    Button reg_btn, btn_login;
    RadioGroup gender_radio_btn;
    ProgressBar login_progess_bar;

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    private static final String TAG = "MainActivity";
    private static final String PREF_USER_MOBILE_PHONE = "9702061635";
    private static final int SMS_PERMISSION_CODE = 0;
    private String mUserMobilePhone ;
    private SharedPreferences mSharedPreferences;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //Hooks
        reg_name = findViewById(R.id.reg_name);
        reg_email = findViewById(R.id.reg_email);
        reg_phone = findViewById(R.id.reg_phone);
        reg_password = findViewById(R.id.reg_password);
        reg_btn = findViewById(R.id.reg_btn);
        btn_login = findViewById(R.id.btn_login);
        gender_radio_btn = findViewById(R.id.gender_radio_btn);
        login_progess_bar = findViewById(R.id.login_progess_bar);

        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }

        initViews();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserMobilePhone = mSharedPreferences.getString(PREF_USER_MOBILE_PHONE, "");
        if (!TextUtils.isEmpty(mUserMobilePhone)) {
            reg_phone.getEditText().setText(mUserMobilePhone);
        }

    }

    public void btn_login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish(); //
    }

    private Boolean validateName () {
        String val = reg_name.getEditText().getText().toString();
        if(val.isEmpty()) {
            reg_name.setError("Field cannot be empty");
            return false;
        }
        else{
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail () {
        String val = reg_email.getEditText().getText().toString();

        if(val.isEmpty()) {
            reg_email.setError("Field cannot be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            reg_email.setError("Enter valid E-mail");
            return false;
        }
        else{
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone () {
        String val = reg_phone.getEditText().getText().toString();
        if(val.isEmpty()) {
            reg_phone.setError("Field cannot be empty");
            return false;
        }
        else{
            reg_phone.setError(null);
            reg_phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword () {
        String val = reg_password.getEditText().getText().toString();
        if(val.isEmpty()) {
            reg_password.setError("Field cannot be empty");
            return false;
        }
        else if (!Password_Pattern.matcher(val).matches()) {
            reg_password.setError("Password is too weak");
            return false;
        }
        else{
            reg_password.setError(null);
            reg_password.setErrorEnabled(false);
            return true;
        }
    }



    //save data in firebase on button click
    public void reg_btn(View view) {

        String name = reg_name.getEditText().getText().toString();
        String email = reg_email.getEditText().getText().toString();
        String phone = reg_phone.getEditText().getText().toString();
        String password = reg_password.getEditText().getText().toString();

        int checkId = gender_radio_btn.getCheckedRadioButtonId();
        RadioButton selected_gender = gender_radio_btn.findViewById(checkId);
        if (selected_gender == null) {
            Toast.makeText(SignUp.this, "Select Gender", Toast.LENGTH_SHORT).show();
        }
        else {
            final String gender = selected_gender.getText().toString();
            //validate
            if(!validateName() | !validateEmail() | !validatePhone() | !validatePassword()) {
                return;
            }
            else{
                register(name, email, phone, password, gender);
            }
        }
    }

    private void register(String name, String email, String phone, String password, String gender) {
        login_progess_bar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                FirebaseUser rUser = firebaseAuth.getCurrentUser(); //
                String userID = rUser.getUid();  //
                reference = rootNode.getReference("users").child(userID); //realtimedb
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userId", userID);
                hashMap.put("name", name);
                hashMap.put("email", email);
                hashMap.put("phone", phone);
                hashMap.put("password", password);
                hashMap.put("gender", gender);
                // specify if the user is Client
                hashMap.put("type", "Client");

//                if (!hasValidPreConditions()) return;
//                checkAndUpdateUserPrefNumber();
//
//                com.example.drivingschool.SmsHelper.sendDebugSms(String.valueOf(reg_phone.getEditText().getText()),
//                        com.example.drivingschool.SmsHelper.SMS_CONDITION
//                        + " This SMS is Automatically send, Hello" + name);
//                Toast.makeText(getApplicationContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();

                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    login_progess_bar.setVisibility(View.GONE);
                    if (task1.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this,Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SignUp.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                login_progess_bar.setVisibility(View.GONE);
                Toast.makeText(SignUp.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        reg_phone = findViewById(R.id.reg_phone);
//        findViewById(R.id.btn_normal_sms).setOnClickListener(this);
//        findViewById(R.id.btn_conditional_sms).setOnClickListener(this);
    }

    /**
     * Checks if stored SharedPreferences value needs updating and updates \o/
     */
    private void checkAndUpdateUserPrefNumber() {
        if (TextUtils.isEmpty(mUserMobilePhone) && !mUserMobilePhone.equals(reg_phone.getEditText().getText().toString())) {
            mSharedPreferences
                    .edit()
                    .putString(PREF_USER_MOBILE_PHONE, reg_phone.getEditText().getText().toString())
                    .apply();
        }
    }


    /**
     * Validates if the app has readSmsPermissions and the mobile phone is valid
     *
     * @return boolean validation value
     */
    private boolean hasValidPreConditions() {
        if (!hasReadSmsPermission()) {
            requestReadAndSendSmsPermission();
            return false;
        }

        if (!com.example.drivingschool.SmsHelper.isValidPhoneNumber(reg_phone.getEditText().getText().toString())) {
            Toast.makeText(getApplicationContext(), R.string.error_invalid_phone_number, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(SignUp.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(SignUp.this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this, Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(SignUp.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
    }

}