package com.learnandroid.chatapplication.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.learnandroid.chatapplication.R;

import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.databaseReference;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;

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
                        if(dataSnapshot.getValue().equals("Online"))
                            getSupportActionBar().setSubtitle("Online");
                        else
                            getSupportActionBar().setSubtitle("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
