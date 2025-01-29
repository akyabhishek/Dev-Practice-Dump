package com.mrabk.edtecheducatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registerform extends AppCompatActivity {
    private String mobilefromverify,profileemail,profilephone,profilename;
    private TextView mobnumtext;
    private String edittextphone;
    private EditText edittextemail, edittextname;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        mobilefromverify = getIntent().getStringExtra("mobile");
        mobnumtext = findViewById(R.id.textMobile);
        mobnumtext.setText(String.format(
                "+91" +mobilefromverify
        ));
        edittextname=(EditText) findViewById(R.id.editTextName);
        edittextphone=mobilefromverify;
        edittextemail=(EditText) findViewById(R.id.editTextEmail);

        register=(Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileemail = edittextemail.getText().toString().trim();
                profilephone = edittextphone;
                profilename = edittextname.getText().toString().trim();
                if(profilename.isEmpty()){
                    edittextname.setError("Full name required");
                    edittextname.requestFocus();
                    return;
                }
                if(profileemail.isEmpty()){
                    edittextemail.setError("Email is required");
                    edittextemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(profileemail).matches()){
                    edittextemail.setError("Please provide valid email");
                    edittextemail.requestFocus();
                    return;
                }
                Toast.makeText(registerform.this, "Hello2 "+profilename + profilephone +profileemail, Toast.LENGTH_LONG).show();
                User user=new User(profilename,profilephone,profileemail);
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(registerform.this, "User's data is stored", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(registerform.this, "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}