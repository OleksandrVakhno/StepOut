package com.troyhack.stepout;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter {


    private List<Messages> messagesList;
    private FirebaseAuth auth;
    private DatabaseReference usersRef;

    public MessageAdapter(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_layout, parent, false);
        auth = FirebaseAuth.getInstance();
        return new MessageViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        String uid = auth.getCurrentUser().getUid();
        Messages message = messagesList.get(position);

        String fromId = message.getFrom();
        String messageType = message.getType();


        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromId);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("image")){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(messageType.equals("text")){
            ((MessageViewHolder)holder).receiverMessage.setVisibility(View.INVISIBLE);
            ((MessageViewHolder)holder).profileImage.setVisibility(View.INVISIBLE);
            ((MessageViewHolder)holder).receiverDate.setVisibility(View.INVISIBLE);
            ((MessageViewHolder)holder).receiverTime.setVisibility(View.INVISIBLE);
            ((MessageViewHolder)holder).receiverName.setVisibility(View.INVISIBLE);
            if(fromId.equals(uid)){
                ((MessageViewHolder)holder).senderDate.setText(message.getDate());
                ((MessageViewHolder)holder).senderTime.setText(message.getTime());
                ((MessageViewHolder)holder).senderMessage.setBackgroundResource(R.drawable.sender_messages_layout);
                ((MessageViewHolder)holder).senderMessage.setTextColor(Color.BLACK);
                ((MessageViewHolder)holder).senderMessage.setText(message.getMessage());
            }else {
                ((MessageViewHolder)holder).senderMessage.setVisibility(View.INVISIBLE);
                ((MessageViewHolder)holder).senderTime.setVisibility(View.INVISIBLE);
                ((MessageViewHolder)holder).senderDate.setVisibility(View.INVISIBLE);

                ((MessageViewHolder)holder).receiverMessage.setVisibility(View.VISIBLE);
                ((MessageViewHolder)holder).profileImage.setVisibility(View.VISIBLE);
                ((MessageViewHolder)holder).receiverName.setVisibility(View.VISIBLE);
                ((MessageViewHolder)holder).receiverDate.setVisibility(View.VISIBLE);
                ((MessageViewHolder)holder).receiverTime.setVisibility(View.VISIBLE);


                ((MessageViewHolder)holder).receiverName.setText(message.getName());
                ((MessageViewHolder)holder).receiverMessage.setBackgroundResource(R.drawable.receiver_message_layout);
                ((MessageViewHolder)holder).receiverMessage.setTextColor(Color.BLACK);
                ((MessageViewHolder)holder).receiverMessage.setText(message.getMessage());
                ((MessageViewHolder)holder).receiverDate.setText(message.getDate());
                ((MessageViewHolder)holder).receiverTime.setText(message.getTime());
            }
        }

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }




    public class MessageViewHolder extends RecyclerView.ViewHolder{


        public TextView senderMessage, receiverName, receiverMessage, senderTime, senderDate, receiverTime, receiverDate;
        public CircleImageView profileImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessage = (TextView) itemView.findViewById(R.id.senderMessageText);
            receiverMessage = (TextView) itemView.findViewById(R.id.receiverMessageText);
            profileImage = (CircleImageView) itemView.findViewById(R.id.messageProfileImage);
            receiverName = (TextView) itemView.findViewById(R.id.receiverUsername);
            senderTime = (TextView) itemView.findViewById(R.id.senderTime);
            senderDate = (TextView) itemView.findViewById(R.id.senderDate);
            receiverTime = (TextView) itemView.findViewById(R.id.receiverTime);
            receiverDate = (TextView) itemView.findViewById(R.id.receiverDate);


        }




    }


}
