package com.example.welcome.chatapp.FirebaseUtil;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by welcome on 17-04-2017.
 */

public class FireBaseAuthUtility {
    private static FireBaseAuthUtility mInstance;
    private FirebaseAuth mFirebaseAuth;

    //Singleton Class
    private FireBaseAuthUtility() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    //Instance function
    public static FireBaseAuthUtility getInstance() {
        if (mInstance == null) {
            mInstance = new FireBaseAuthUtility();
        }
        return mInstance;
    }

}
