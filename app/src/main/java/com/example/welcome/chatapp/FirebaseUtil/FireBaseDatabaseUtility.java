package com.example.welcome.chatapp.FirebaseUtil;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.welcome.chatapp.Fragments.IndividualChat;
import com.example.welcome.chatapp.Model.Group;
import com.example.welcome.chatapp.Model.User;
import com.example.welcome.chatapp.adapter.IndividualRecAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 17-04-2017.
 */

public class FireBaseDatabaseUtility {
    private static FireBaseDatabaseUtility mInstance;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private List<User > usersList;
    public IndividualRecAdapter adapter;

    //Singleton Class
    private FireBaseDatabaseUtility() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    //Instance method
    public static FireBaseDatabaseUtility getInstance() {
        if (mInstance == null) {
            mInstance = new FireBaseDatabaseUtility();
        }
        return mInstance;
    }

    public void insertNewUser(String userId, User user)
    {
        Log.i("checkIO",userId);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
            //String userId = mDatabaseRef.push().getKey();
        mDatabaseRef.setValue(user);
    }
    //add new group here
    public void insrtNewGroup(Group group)
    {
        mDatabaseRef=FirebaseDatabase.getInstance().getReference("Groupss").push();
        mDatabaseRef.setValue(group);
    }

}

