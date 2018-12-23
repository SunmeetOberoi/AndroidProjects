package com.learnandroid.chatapplication.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.learnandroid.chatapplication.R;
import com.learnandroid.chatapplication.adapter.MessagesRecyclerViewAdapter;
import com.learnandroid.chatapplication.dataClasses.MessagesModel;

import java.util.ArrayList;
import java.util.List;

import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.databaseReference;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    List<MessagesModel> messages = new ArrayList<>();
    RecyclerView rvMessages;
    MessagesRecyclerViewAdapter messagesRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = (Toolbar) findViewById(R.id.chattoolbar);
        toolbar.setTitle(getIntent().getStringExtra("Friend").split("@")[0].replace(',', '.'));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //to show status in the toolbar
        databaseReference.child("Database").child("Users").child(getIntent()
                .getStringExtra("Friend")).child("Status")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue().equals("Online"))
                            getSupportActionBar().setSubtitle("Online");
                        else
                            getSupportActionBar().setSubtitle("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        rvMessages = (RecyclerView) findViewById(R.id.rvMessages);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setStackFromEnd(true);

        rvMessages.setHasFixedSize(true);
        rvMessages.setLayoutManager(llm);
        messagesRecyclerViewAdapter = new MessagesRecyclerViewAdapter(messages, this, 
                getIntent().getStringExtra("Friend"));
        rvMessages.setAdapter(messagesRecyclerViewAdapter);
        
        readMessages();

    }

    private void readMessages() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
