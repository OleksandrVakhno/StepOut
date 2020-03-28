package com.troyhack.stepout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (user==null){
            SendToLogin();
        }
    }



    private void SendToLogin(){
        Intent login = new Intent(MainActivity.this, Login.class);
        startActivity(login);
    }
}
