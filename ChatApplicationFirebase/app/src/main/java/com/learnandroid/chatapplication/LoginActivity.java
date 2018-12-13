package com.learnandroid.chatapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    Button btnSignUp;
    ProgressBar pbLoading;
    FirebaseAuth mAuth;
    String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setViews();
        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInput()) {
                    pbLoading.setVisibility(View.VISIBLE);
                    createUser();
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
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);
    }

    private void createUser() {
        mAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            update(user);
                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            pbLoading.setVisibility(View.GONE);
                            Log.d(TAG, String.valueOf(task.getException()));
                        }
                    }
                });
    }

    private void update(FirebaseUser user) {
        //Call the contacts activity
        Log.d(TAG, "User Created: " + user.getDisplayName());
    }


}
