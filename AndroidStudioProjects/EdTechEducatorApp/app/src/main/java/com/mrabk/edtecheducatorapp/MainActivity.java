package com.mrabk.edtecheducatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
   EditText enternumber;
    Button getotpbutton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressbar_sending_OTP);

        enternumber = findViewById(R.id.input_mobile_number);
        getotpbutton = findViewById(R.id.buttonOTP);
        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enternumber.getText().toString().trim().isEmpty()) {
                    if ((enternumber.getText().toString().trim().length()) == 10) {
                        progressBar.setVisibility(View.VISIBLE);
                        getotpbutton.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(), 60, TimeUnit.SECONDS, MainActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        getotpbutton.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, "Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), verifyOTP.class);
                                        intent.putExtra("mobile", enternumber.getText().toString());
                                        intent.putExtra("backendOTP",backendOTP);
                                        startActivity(intent);
                                    }
                                }
                        );

                    } else {
                        Toast.makeText(MainActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}