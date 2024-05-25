package com.example.adventureplanner.Classes;

import android.app.Application;

import com.google.android.libraries.places.api.Places;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Places SDK
        Places.initialize(getApplicationContext(), "AIzaSyAqWIPYa8fzIZatq4IrcH4hbTBW_v6eYAc");
    }
}
