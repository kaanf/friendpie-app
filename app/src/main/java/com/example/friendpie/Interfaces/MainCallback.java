package com.example.friendpie.Interfaces;

import com.google.firebase.firestore.DocumentReference;

public interface MainCallback {
    void onSignout();
    String onGetUserId();
    DocumentReference getUserDatabase();

}
