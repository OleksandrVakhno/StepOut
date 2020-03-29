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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText emailField, passwordField;
    private Button continueButton;
    private TextView registerButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        initializeFields();
    }




    private void initializeFields(){
        emailField = (EditText)findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        progressDialog = new ProgressDialog(Login.this);
        registerButton = (TextView)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToRegister();
            }
        });
        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    submitLogin(email, password);
                }
            }
        });

    }


    private void submitLogin(String email, String password){

        progressDialog.setTitle("Logging in...");
        progressDialog.setMessage("Please hold while we log you in...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "You are successfully logged in", Toast.LENGTH_SHORT).show();
                    SendToMainActivity();
                }
                else{
                    String error = task.getException().toString();
                    Toast.makeText(Login.this, error, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    private void SendToMainActivity(){
        Intent mainActivityIntent= new Intent(Login.this, MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivityIntent);
        finish();
    }

    private void SendToRegister(){
        Intent registerIntent = new Intent(Login.this, Signup.class);
        startActivity(registerIntent);
    }

}
