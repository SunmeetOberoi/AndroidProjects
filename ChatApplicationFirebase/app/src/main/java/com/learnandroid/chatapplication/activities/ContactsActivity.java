package com.learnandroid.chatapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.learnandroid.chatapplication.R;
import com.learnandroid.chatapplication.adapter.ContactsRecyclerViewAdapter;
import com.learnandroid.chatapplication.dataClasses.ContactsModel;

import java.util.ArrayList;
import java.util.List;

import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.databaseReference;
import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.mAuth;

public class ContactsActivity extends AppCompatActivity {

    List<String> mycontacts = new ArrayList<>();
    List<ContactsModel> contacts = new ArrayList<>();
    RecyclerView rvContacts;
    ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(mAuth.getCurrentUser().getEmail().split("@")[0]);

        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(
                contacts, this);
        getContacts();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvContacts.setLayoutManager(llm);
        rvContacts.setHasFixedSize(true);
        rvContacts.setAdapter(contactsRecyclerViewAdapter);

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
                                // Check if user exists
                                //TODO check if the friend is already added
                                if(!mycontacts.contains(id.replace('.', ','))) {
                                    if (!id.isEmpty()) {
                                        if (!id.equals(mAuth.getCurrentUser().getEmail())) {
                                            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String mid = id.replace('.', ',');
                                                    String userid = mAuth.getCurrentUser().getEmail().replace('.', ',');
                                                    if (dataSnapshot.hasChild(mid)) {
                                                        Toast.makeText(ContactsActivity.this, "Added user", Toast.LENGTH_SHORT).show();
                                                        databaseReference.child("Users").child(userid)
                                                                .child("Contacts").child(mid).setValue(id);
                                                        String chatCode;
                                                        if (userid.compareTo(mid) < 0)
                                                            chatCode = mid + "___" + userid;
                                                        else
                                                            chatCode = userid + "___" + mid;
                                                        databaseReference.child("Messages").child(chatCode)
                                                                .child("no one").setValue("no one");
                                                    } else {
                                                        Toast.makeText(ContactsActivity.this, "User doesn't exists", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        } else {
                                            Toast.makeText(ContactsActivity.this, "You can't add yourself", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                else{
                                    Toast.makeText(ContactsActivity.this, "User Already Added", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create()
                        .show();
            }
        });
    }

    void getContacts(){
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mycontacts.clear();
                for(DataSnapshot ds : dataSnapshot.child(mAuth.getCurrentUser()
                        .getEmail().replace('.', ',')).child("Contacts").getChildren()){
                    mycontacts.add(ds.getKey());
                }
                contacts.clear();
                for(String name : mycontacts){
                    contacts.add(new ContactsModel(name, dataSnapshot.child(name).child("Status")
                            .getValue().toString(), getLastMsg(name)));
                }
                contactsRecyclerViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getLastMsg(String name) {
        String userid = mAuth.getCurrentUser().getEmail().replace('.', ',');
        String chatCode;
        if(userid.compareTo(name) < 0)
            chatCode = name + "___" + userid;
        else
            chatCode = userid + "___" + name;

        databaseReference.child("Messages").child(chatCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    msg = ds.getValue().toString();
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return msg;
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
            case R.id.actionLogOut : setStatus("Offline");
                                     mAuth.signOut();
                                     startActivity(new Intent(this, LoginActivity.class));
                                     this.finish();
                                     break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        setStatus("Offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatus("Online");
    }

    void setStatus(String status){
        try {
            databaseReference.child("Users").child(mAuth.getCurrentUser().getEmail()
                    .replace('.', ',')).child("Status").setValue(status);
        }catch(Exception e){
            if(mAuth.getCurrentUser() != null)
                Toast.makeText(this, "Couldn't do that", Toast.LENGTH_SHORT).show();
        }
    }
}
