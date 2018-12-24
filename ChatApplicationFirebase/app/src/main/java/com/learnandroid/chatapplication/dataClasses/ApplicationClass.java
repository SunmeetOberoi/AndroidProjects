package com.learnandroid.chatapplication.dataClasses;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ApplicationClass extends Application {
    public static FirebaseAuth mAuth;
    public static DatabaseReference databaseReference;
    public static Boolean isAChatOpened = false;

}
