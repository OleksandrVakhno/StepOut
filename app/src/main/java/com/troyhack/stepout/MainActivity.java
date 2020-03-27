package com.troyhack.stepout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (user==null){
            this.SendToSignUp();
        }
    }



    private void SendToSignUp(){
        Intent signUp = new Intent(MainActivity.this, Signup.class);
        startActivity(signUp);
    }
}
