package com.learnandroid.chatapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.learnandroid.chatapplication.R;

import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.databaseReference;
import static com.learnandroid.chatapplication.dataClasses.ApplicationClass.mAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    Button btnSignUp;
    ProgressBar pbLoading;
    String TAG = "signin/singup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (mAuth.getCurrentUser() != null)
            showContacts();

        setViews();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validInput()) {
                    pbLoading.setVisibility(View.VISIBLE);
                    createUser();
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validInput()) {
                    pbLoading.setVisibility(View.VISIBLE);
                    signinUser();
                }
            }
        });

    }

    private void signinUser() {
        mAuth.signInWithEmailAndPassword(etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            databaseReference.child("Database").child("Users").child(mAuth.getCurrentUser().getEmail()
                                    .replace('.', ',')).child("Status").setValue("Online");
                            showContacts();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    String.valueOf(task.getException().getMessage()), Toast.LENGTH_SHORT)
                                    .show();
                            pbLoading.setVisibility(View.GONE);
                            Log.d(TAG, String.valueOf(task.getException()));
                        }
                    }
                });
    }

    private boolean validInput() {
        if (etEmail.getText().toString().trim().isEmpty() ||
                etPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter complete information", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    private void setViews() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
    }

    private void createUser() {
        mAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            databaseReference.child("Database").child("Users").child(mAuth.getCurrentUser().getEmail()
                                    .replace('.', ',')).child("Email")
                                    .setValue(mAuth.getCurrentUser().getEmail());
                            databaseReference.child("Database").child("Users").child(mAuth.getCurrentUser().getEmail()
                                    .replace('.', ',')).child("Status")
                                    .setValue("Online");
                            showContacts();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    String.valueOf(task.getException().getMessage()), Toast.LENGTH_SHORT).show();
                            pbLoading.setVisibility(View.GONE);
                            Log.d(TAG, String.valueOf(task.getException()));
                        }
                    }
                });
    }

    private void showContacts() {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}
