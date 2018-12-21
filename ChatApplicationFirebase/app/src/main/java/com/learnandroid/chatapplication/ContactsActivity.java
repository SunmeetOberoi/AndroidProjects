package com.learnandroid.chatapplication;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
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

    DatabaseReference database;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance().getReference();
        userid = mAuth.getCurrentUser().getEmail().replace('.', ',');
        toolbar.setTitle(userid.replace(',', '.').split("@")[0]);

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
                                    database.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                                                if (id.equals(ds.child("Email").getValue(String.class))) {
                                            if(dataSnapshot.hasChild(id.replace('.', ','))){
                                                Toast.makeText(ContactsActivity.this, "Added user", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(ContactsActivity.this, "User doesn't exists", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contacts_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.actionLogOut : mAuth.signOut();
                                     startActivity(new Intent(this, LoginActivity.class));
                                     this.finish();
                                     break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.child("Users").child(userid).child("Status").setValue("Offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        database.child("Users").child(userid).child("Status").setValue("Online");
    }
}
