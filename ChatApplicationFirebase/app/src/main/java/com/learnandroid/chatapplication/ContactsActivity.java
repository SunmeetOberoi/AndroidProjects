package com.learnandroid.chatapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.learnandroid.chatapplication.ApplicationClass.mAuth;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(mAuth.getCurrentUser().getEmail().split("@")[0]);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

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
                                final String id = etFriendsEmail.getText().toString();
                                if(!id.isEmpty()){
                                    database.child("Users").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            TODO:Searching is not efficient
                                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                                if (id.equals(ds.child("Email").getValue(String.class)))
                                                    Toast.makeText(ContactsActivity.this, "Added User", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }).create()
                        .show();
            }
        });


    }

}
