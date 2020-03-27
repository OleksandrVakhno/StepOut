package com.troyhack.stepout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText fullNameField, emailField, passwordField, confirmField;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeFields();
    }


    private void initializeFields(){
        fullNameField = (EditText)findViewById(R.id.fullNameField);
        emailField = (EditText)findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        confirmField = (EditText) findViewById(R.id.confirmField);
        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = fullNameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPass = confirmField.getText().toString();
                if (TextUtils.isEmpty(fullName)){
                    Toast.makeText(Signup.this, "Full name can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email)){
                    Toast.makeText(Signup.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email)){
                    Toast.makeText(Signup.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (!TextUtils.equals(password, confirmPass)){
                    Toast.makeText(Signup.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
                else{
                    submitRegistration(fullName, email, password);
                }
            }
        });
    }


    private boolean submitRegistration(String name, String email, String password){
        return true;
    }


}
