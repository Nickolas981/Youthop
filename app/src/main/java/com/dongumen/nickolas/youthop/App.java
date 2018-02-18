package com.dongumen.nickolas.youthop;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }
}
