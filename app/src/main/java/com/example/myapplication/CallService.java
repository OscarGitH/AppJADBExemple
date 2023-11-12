package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class CallService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String phoneNumber = intent.getStringExtra("phone_number");
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                makePhoneCall(phoneNumber);
            }
        }
        return START_NOT_STICKY;
    }

    private void makePhoneCall(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_CALL);
        dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }
}