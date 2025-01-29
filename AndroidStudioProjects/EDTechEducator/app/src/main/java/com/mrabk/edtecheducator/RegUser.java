package com.mrabk.edtecheducator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class RegUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner, reguser;
    private EditText edittextname, edittextphone, edittextpassword, edittextemail;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_user);
        mAuth=FirebaseAuth.getInstance();

        banner=(TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        reguser=(Button) findViewById(R.id.reguser);
        reguser.setOnClickListener(this);

        edittextname=(EditText) findViewById(R.id.editName);
        edittextphone=(EditText) findViewById(R.id.editTextPhone);
        edittextemail=(EditText) findViewById(R.id.editTextEmail);
        edittextpassword=(EditText) findViewById(R.id.editTextPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.reguser:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String email = edittextemail.getText().toString().trim();
        String password = edittextpassword.getText().toString().trim();
        String phone = edittextphone.getText().toString().trim();
        String name = edittextname.getText().toString().trim();

        if(name.isEmpty()){
            edittextname.setError("Full name required");
            edittextname.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            edittextphone.setError("Phone number is required");
            edittextphone.requestFocus();
            return;
        }
        if(email.isEmpty()){
            edittextemail.setError("Email is required");
            edittextemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edittextemail.setError("Please provide valid email");
            edittextemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edittextpassword.setError("Password is required");
            edittextpassword.requestFocus();
            return;
        }
        if(password.length()<6){
            edittextpassword.setError("Atleast enter 6 character for valid password");
            edittextpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user= new User(name,phone,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegUser.this, "User Registered", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegUser.this,ProfileActivity.class));

                                    }
                                    else{
                                        Toast.makeText(RegUser.this,"Error!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility((View.GONE));
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(RegUser.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility((View.GONE));
                        }
                    }
                });
    }
}