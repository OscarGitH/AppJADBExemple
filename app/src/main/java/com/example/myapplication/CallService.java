package com.example.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.media.AudioManager;

import java.io.IOException;

public class CallService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                handleAction(action, intent);
            }
        }
        return START_NOT_STICKY;
    }

    private void handleAction(String action, Intent intent) {
        switch (action) {
            case "makePhoneCall":
                String phoneNumber = intent.getStringExtra("phone_number");
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    makePhoneCall(phoneNumber);
                }
                break;
            case "endCall":
                endCall();
                break;
            case "setSpeakerphoneOn":
                boolean on = intent.getBooleanExtra("on", false);
                setSpeakerphoneOn(on);
                break;
        }
    }

    private void makePhoneCall(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_CALL);
        dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }

    private void endCall() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> c = Class.forName(telephonyManager.getClass().getName());
            java.lang.reflect.Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService = m.invoke(telephonyManager);
            c = Class.forName(telephonyService.getClass().getName());
            m = c.getDeclaredMethod("endCall");
            m.setAccessible(true);
            m.invoke(telephonyService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpeakerphoneOn(boolean on) {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(on);
    }
}