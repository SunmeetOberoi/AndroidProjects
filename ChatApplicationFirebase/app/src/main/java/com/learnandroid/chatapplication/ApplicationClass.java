package com.learnandroid.chatapplication;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ApplicationClass extends Application {
    public static FirebaseAuth mAuth;
    public static DatabaseReference databaseReference;

}
