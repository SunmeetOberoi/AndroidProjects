package com.learnandroid.chatapplication.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.learnandroid.chatapplication.R;
import com.learnandroid.chatapplication.adapter.MessagesRecyclerViewAdapter;
import com.learnandroid.chatapplication.dataClasses.MessagesModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.databaseReference;
import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.isAChatOpened;
import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.mAuth;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    List<MessagesModel> messages = new ArrayList<>();
    RecyclerView rvMessages;
    ImageButton ibSendMessage;
    EditText etMessage;
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

        etMessage = (EditText) findViewById(R.id.etMessage);
        ibSendMessage = (ImageButton) findViewById(R.id.ibSendMessage);
        ibSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etMessage.getText().toString().trim().isEmpty()) {
                    HashMap<String, String> newMessage = new HashMap<>();
                    newMessage.put("from", mAuth.getCurrentUser().getEmail().replace('.', ','));
                    newMessage.put("value", etMessage.getText().toString());
                    databaseReference.child("Database").child("Messages").child(getIntent()
                            .getStringExtra("ChatCode")).push().setValue(newMessage);
                    etMessage.setText("");
                }
            }
        });

    }

    private void readMessages() {

        //get only 50 last messages
        Query query = databaseReference.child("Database").child("Messages").child(getIntent()
                .getStringExtra("ChatCode")).limitToLast(50);

        query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        messages.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            MessagesModel message = ds.getValue(MessagesModel.class);
                            if(!message.getFrom().equals("no one"))
                                messages.add(message);
                        }
                        messagesRecyclerViewAdapter.notifyDataSetChanged();
                        rvMessages.scrollToPosition(messagesRecyclerViewAdapter.getItemCount()-1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    Boolean backToContacts = false;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToContacts = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!backToContacts)
            setStatus("Offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatus("Online");
    }

    void setStatus(String status) {
        try {
            databaseReference.child("Database").child("Users").child(mAuth.getCurrentUser().getEmail()
                    .replace('.', ',')).child("Status").setValue(status);
        } catch (Exception e) {
            if (mAuth.getCurrentUser() != null)
                Toast.makeText(this, "Couldn't do that", Toast.LENGTH_SHORT).show();
        }
    }

}
