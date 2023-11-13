package com.example.myapplication;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String phoneNumber = intent.getStringExtra("phoneNumber");

        if (firstName != null && phoneNumber != null) {
            addContact(firstName, lastName, phoneNumber);
        }

        stopSelf();
        return START_NOT_STICKY;
    }

    private void addContact(String firstName, String lastName, String phoneNumber) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactsContract.RawContacts.ACCOUNT_NAME, (String) null);
            contentValues.put(ContactsContract.RawContacts.ACCOUNT_TYPE, (String) null);

            // Insert raw contact
            android.net.Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
            String rawContactId = rawContactUri.getLastPathSegment();

            // Insert name
            ContentValues nameContentValues = new ContentValues();
            nameContentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            nameContentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            nameContentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstName);

            if (lastName != null && !lastName.trim().isEmpty()) {
                nameContentValues.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastName);
            }

            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, nameContentValues);

            // Insert phone number
            ContentValues phoneContentValues = new ContentValues();
            phoneContentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            phoneContentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            phoneContentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);

            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, phoneContentValues);

            Log.d("ContactService", "Contact added: " + firstName + " " + lastName + " - " + phoneNumber);
        } catch (Exception e) {
            Log.e("ContactService", "Error adding contact: " + e.getMessage());
        }
    }
}