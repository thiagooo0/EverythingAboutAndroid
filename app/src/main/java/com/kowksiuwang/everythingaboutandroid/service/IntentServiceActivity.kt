package com.kowksiuwang.everythingaboutandroid.service

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.kowksiuwang.everythingaboutandroid.R

class IntentServiceActivity : AppCompatActivity() {
    var myTaskId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_service)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(this@IntentServiceActivity, MyIntentService::class.java)
            intent.action = " Task${myTaskId++}"
            startService(intent)
        }
    }
}