package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

public class SMSServiceSend extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String message = intent.getStringExtra("message");

        if (phoneNumber != null && !phoneNumber.trim().isEmpty() &&
                message != null && !message.trim().isEmpty()) {
            sendSMS(phoneNumber, message);
        }

        stopSelf();
        return START_NOT_STICKY;
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.d("SMSService", "SMS sent to " + phoneNumber + ": " + message);
        } catch (Exception e) {
            Log.e("SMSService", "Error sending SMS: " + e.getMessage());
        }
    }
}