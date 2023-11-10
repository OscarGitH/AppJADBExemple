package com.example.myapplication

import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.os.IBinder
import android.provider.ContactsContract
import android.util.Log

class ContactService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val firstName = intent?.getStringExtra("firstName")
        val lastName = intent?.getStringExtra("lastName")
        val phoneNumber = intent?.getStringExtra("phoneNumber")

        if (firstName != null && phoneNumber != null) {
            addContact(firstName, lastName, phoneNumber)
        }

        stopSelf()
        return START_NOT_STICKY
    }

    private fun addContact(firstName: String, lastName: String?, phoneNumber: String) {
        try {
            val contentValues = ContentValues()
            contentValues.put(ContactsContract.RawContacts.ACCOUNT_NAME, null as String?)
            contentValues.put(ContactsContract.RawContacts.ACCOUNT_TYPE, null as String?)

            val rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, contentValues)
            val rawContactId = rawContactUri?.lastPathSegment

            val nameContentValues = ContentValues()
            nameContentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            nameContentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            nameContentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstName)

            if (!lastName.isNullOrBlank()) {
                nameContentValues.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastName)
            }

            contentResolver.insert(ContactsContract.Data.CONTENT_URI, nameContentValues)

            val phoneContentValues = ContentValues()
            phoneContentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            phoneContentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            phoneContentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)

            contentResolver.insert(ContactsContract.Data.CONTENT_URI, phoneContentValues)

            Log.d("ContactService", "Contact added: $firstName $lastName - $phoneNumber")
        } catch (e: Exception) {
            Log.e("ContactService", "Error adding contact: ${e.message}")
        }
    }
}