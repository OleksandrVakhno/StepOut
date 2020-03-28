package com.troyhack.stepout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;

public class Signup extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText fullNameField, emailField, passwordField, confirmField;
    private Button continueButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeFields();
        auth = FirebaseAuth.getInstance();
    }


    private void initializeFields(){
        fullNameField = (EditText)findViewById(R.id.fullNameField);
        emailField = (EditText)findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        confirmField = (EditText) findViewById(R.id.confirmField);
        progressDialog = new ProgressDialog(Signup.this);
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
                else if (TextUtils.isEmpty(password)){
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


    private void submitRegistration(String name, String email, String password){


        progressDialog.setTitle("Creating the account...");
        progressDialog.setMessage("Please hold while we create an account for you...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Signup.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    SendToLogin();
                }
                else{
                    String error= task.getException().toString();
                    Toast.makeText(Signup.this, error, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    private void SendToLogin(){
        Intent loginIntent = new Intent(Signup.this, Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }


}
