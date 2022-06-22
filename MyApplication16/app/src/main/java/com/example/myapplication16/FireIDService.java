package com.example.myapplication16;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;


public class FireIDService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Task<String> tkn = FirebaseMessaging.getInstance().getToken();
        Log.e("NEW_TOKEN",s);
    }
}
