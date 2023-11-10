package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log

class SMSServiceSend : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val phoneNumber = intent?.getStringExtra("phoneNumber")
        val message = intent?.getStringExtra("message")

        if (!phoneNumber.isNullOrBlank() && !message.isNullOrBlank()) {
            sendSMS(phoneNumber, message)
        }

        stopSelf()
        return START_NOT_STICKY
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("SMSService", "SMS sent to $phoneNumber: $message")
        } catch (e: Exception) {
            Log.e("SMSService", "Error sending SMS: ${e.message}")
        }
    }
}
