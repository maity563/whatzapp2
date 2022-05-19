package com.maity.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maity.whatsapp.Adapter.ChatAdapter;
import com.maity.whatsapp.Models.MessageModel;
import com.maity.whatsapp.databinding.ActivityChatDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase firebaseDatabase;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        firebaseDatabase=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        final String senderid=auth.getUid();
        String receiveid=getIntent().getStringExtra("userId");
        String username=getIntent().getStringExtra("Username");
        String profilepicture=getIntent().getStringExtra("Profile Picture");

        binding.textView.setText(username);
        Picasso.get().load(profilepicture).placeholder(R.drawable.profile_chat_dp).into(binding.profileImage);

        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        final ArrayList<MessageModel> messageModels=new ArrayList<>();

        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this);

        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String sender_room = senderid+receiveid;
        final String receiver_room= receiveid + senderid;

        firebaseDatabase.getReference().child("chats").child(sender_room).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                messageModels.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    MessageModel model=snapshot1.getValue(MessageModel.class);
                    model.setMessageId(snapshot1.getKey());
                    messageModels.add(model);
                }

                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg =   binding.message.getText().toString();
                final MessageModel model = new MessageModel(senderid,msg);

                model.setTimestamp(new Date().getTime());

                binding.message.setText("");

                firebaseDatabase.getReference().child("chats").child(sender_room).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        firebaseDatabase.getReference().child("chats").child(receiver_room).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });




            }
        });




    }
}