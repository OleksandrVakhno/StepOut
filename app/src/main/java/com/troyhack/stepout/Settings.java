package com.troyhack.stepout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {



    private EditText name, status;
    private CircleImageView image;
    private Button update;
    private FirebaseAuth auth;
    private DatabaseReference db;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference();
        InintializeFields();
        RetrieveUserInfo();

    }




    private void InintializeFields(){
        name = (EditText) findViewById(R.id.set_name);
        status = (EditText) findViewById(R.id.set_status);
        image = (CircleImageView) findViewById(R.id.profile_image);
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateInfo();
            }
        });
    }

    private void RetrieveUserInfo(){
        db.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String fullname  = dataSnapshot.child("name").getValue().toString();
                    String statusInfo  = dataSnapshot.child("status").getValue().toString();
                    name.setText(fullname);
                    status.setText(statusInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void UpdateInfo(){
        String newName = name.getText().toString();
        String newStatus = status.getText().toString();


        if(TextUtils.isEmpty(newName)){
            Toast.makeText(Settings.this, "Please write your name first...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(newStatus)){
            Toast.makeText(Settings.this, "Please write your status first...", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,String> vals = new HashMap<>();
            vals.put("uid", uid);
            vals.put("name", newName);
            vals.put("status", newStatus);
            db.child("Users").child(uid).setValue(vals).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Settings.this, "Info updated...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Settings.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
