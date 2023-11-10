package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : Activity() {

    private val MY_PERMISSIONS_REQUEST_SEND_SMS = 2
    private val MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vérifier si l'autorisation d'envoyer des SMS est déjà accordée
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si l'autorisation n'est pas accordée, demander à l'utilisateur
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                MY_PERMISSIONS_REQUEST_SEND_SMS
            )
        } else {
            // Si l'autorisation est déjà accordée, passer à la demande suivante
            requestWriteContactsPermission()
        }
    }

    private fun requestWriteContactsPermission() {
        // Vérifier si l'autorisation de lire les contacts est déjà accordée
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si l'autorisation n'est pas accordée, demander à l'utilisateur
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CONTACTS),
                MY_PERMISSIONS_REQUEST_WRITE_CONTACTS
            )
        } else {
            // Si l'autorisation est déjà accordée, fermer l'activité
            finish()
        }
    }

    // Ajouter la méthode onRequestPermissionsResult pour traiter la réponse de l'utilisateur
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_SEND_SMS -> {
                // Vérifier si l'autorisation d'envoyer des SMS a été accordée
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // L'autorisation a été accordée, passer à la demande suivante
                    requestWriteContactsPermission()
                } else {
                    // L'utilisateur a refusé l'autorisation, gérer en conséquence
                }
            }
            MY_PERMISSIONS_REQUEST_WRITE_CONTACTS -> {
                // Vérifier si l'autorisation de lire les contacts a été accordée
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // L'autorisation a été accordée, fermer l'activité
                    finish()
                } else {
                    // L'utilisateur a refusé l'autorisation, gérer en conséquence
                }
            }
        }
    }
}