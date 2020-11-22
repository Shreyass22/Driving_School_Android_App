package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgetPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    ProgressBar reset;
    TextView resetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();
        TextInputLayout reset_mail = findViewById(R.id.reset_mail);
        resetText = findViewById(R.id.resetText);
        Button sendMessage = findViewById(R.id.sendMessage);
        reset = findViewById(R.id.reset);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset.setVisibility(View.VISIBLE);
                mAuth.fetchSignInMethodsForEmail(reset_mail.getEditText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            reset.setVisibility(View.GONE);
                            resetText.setText("This is not registeres e-mail id, you can create new account");
                        }
                        else {
                            mAuth.sendPasswordResetEmail(reset_mail.getEditText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    reset.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        resetText.setText("A mail to reset password has been send to you mail address");
                                    }
                                    else {
                                        resetText.setText(task.getException().getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

    }
}