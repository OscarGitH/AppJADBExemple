package com.example.myapplication

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Laissez cette partie vide pour que l'activité ne fasse rien d'autre que démarrer le service en arrière-plan.
        finish()
    }
}
