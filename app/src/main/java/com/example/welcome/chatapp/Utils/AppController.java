package com.example.welcome.chatapp.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by welcome on 18-04-2017.
 */

public class AppController extends Application {
    public static final String PREFS_NAME = "CHAT_APP";
    public static SharedPreferences prefs;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mInstance = this;
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
}
