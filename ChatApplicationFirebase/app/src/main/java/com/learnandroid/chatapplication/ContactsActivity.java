package com.learnandroid.chatapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbrefr = db.getReference("messages");
        dbrefr.child("from").setValue("sunmeet.oberoi@gmail.com");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ContactsActivity.this);
                View dialogView = LayoutInflater.from(ContactsActivity.this).inflate(R.layout.add_contact_dialog, null);
                alert.setView(dialogView);
                final EditText etFriendsEmail = (EditText) dialogView.findViewById(R.id.etFriendsEmail);
                alert
                        .setTitle("Add Contact")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = etFriendsEmail.getText().toString().trim();
                                if(!id.isEmpty()){
                                    Log.d("contacts", id);
                                }
                            }
                        }).create()
                        .show();
            }
        });
    }

}
