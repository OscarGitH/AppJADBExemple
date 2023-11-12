package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vérifier si l'autorisation d'envoyer des SMS est déjà accordée
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
        ) != PackageManager.PERMISSION_GRANTED) {
            // Si l'autorisation n'est pas accordée, demander à l'utilisateur
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS
            );
        } else {
            // Si l'autorisation est déjà accordée, passer à la demande suivante
            requestWriteContactsPermission();
        }
    }

    private void requestWriteContactsPermission() {
        // Vérifier si l'autorisation de lire les contacts est déjà accordée
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED) {
            // Si l'autorisation n'est pas accordée, demander à l'utilisateur
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_CONTACTS},
                    MY_PERMISSIONS_REQUEST_WRITE_CONTACTS
            );
        } else {
            // Si l'autorisation est déjà accordée, fermer l'activité
            finish();
        }
    }

    // Ajouter la méthode onRequestPermissionsResult pour traiter la réponse de l'utilisateur
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
                // Vérifier si l'autorisation d'envoyer des SMS a été accordée
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // L'autorisation a été accordée, passer à la demande suivante
                    requestWriteContactsPermission();
                } else {
                    // L'utilisateur a refusé l'autorisation, gérer en conséquence
                }
                break;
            case MY_PERMISSIONS_REQUEST_WRITE_CONTACTS:
                // Vérifier si l'autorisation de lire les contacts a été accordée
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // L'autorisation a été accordée, fermer l'activité
                    finish();
                } else {
                    // L'utilisateur a refusé l'autorisation, gérer en conséquence
                }
                break;
        }
    }
}