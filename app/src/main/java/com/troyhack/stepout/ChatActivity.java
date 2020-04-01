package com.troyhack.stepout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class ChatActivity extends AppCompatActivity {


    private ActionBar toolbar;
    private ImageButton sendMessageButton;
    private EditText messageEditText;
    private ScrollView scrollView;
    private TextView displayMessage;


    private String uid, username, date, time;
    private FirebaseAuth auth;
    private DatabaseReference usersRef, groupchatRef;



    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        auth= FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        groupchatRef = FirebaseDatabase.getInstance().getReference().child("ChatRoom");
        uid = auth.getCurrentUser().getUid();

        initializeFields();
        getUsersInfo();
    }


    @Override
    protected void onStart() {
        super.onStart();


        groupchatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 if(dataSnapshot.exists()){
                     DisplayMessages(dataSnapshot);
                 }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initializeFields(){
        toolbar = getSupportActionBar();
        toolbar.setTitle("Chat Room");

        sendMessageButton = (ImageButton) findViewById(R.id.sendmessageButton);
        messageEditText = (EditText) findViewById(R.id.chatEditText);
        scrollView = (ScrollView) findViewById(R.id.scrollChatView);
        displayMessage = (TextView) findViewById(R.id.groupChatTextView);


        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                messageEditText.setText("");
            }
        });

    }


    private void getUsersInfo(){
        usersRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    username = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void sendMessage(){


        String message = messageEditText.getText().toString();


        if(TextUtils.isEmpty(message)){
            Toast.makeText(ChatActivity.this, "Please input the message first", Toast.LENGTH_SHORT).show();

        }else{

            Calendar calendar = Calendar.getInstance();
            date = dateFormat.format(calendar.getTime());
            time = timeFormat.format(calendar.getTime());

            Toast.makeText(ChatActivity.this, message+date+time, Toast.LENGTH_SHORT).show();

            String messageKey = groupchatRef.push().getKey();

            HashMap<String, Object> messageInfo = new HashMap<>();
            messageInfo.put("name", username);
            messageInfo.put("message", message);
            messageInfo.put("date", date);
            messageInfo.put("time", time);

            groupchatRef.child(messageKey).setValue(messageInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(ChatActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            scrollView.fullScroll(ScrollView.FOCUS_DOWN);


        }





    }


    private void DisplayMessages(DataSnapshot dataSnapshot){
        Iterator it = dataSnapshot.getChildren().iterator();

        while (it.hasNext()){
            String chatDate = (String)((DataSnapshot)it.next()).getValue();
            String chatMessage = (String)((DataSnapshot)it.next()).getValue();
            String chatName = (String)((DataSnapshot)it.next()).getValue();
            String chatTime = (String)((DataSnapshot)it.next()).getValue();

            displayMessage.append(chatName +" :\n"+ chatMessage+" \n"+chatTime+"     "+ chatDate+"\n\n\n");
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }


}
