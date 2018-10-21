package com.example.jordanwillis.androidlabs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

class StartActivity : Activity() {

    val ACTIVITY_NAME = "StartActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val chatButton = findViewById<Button>(R.id.startChatButton)
        chatButton.setOnClickListener {
            Log.i(ACTIVITY_NAME, "User clicked Start Chat")
        }
        chatButton.setOnClickListener {
            val intent = Intent(this@StartActivity, ChatWindow::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        Log.i(ACTIVITY_NAME, "In onResume()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(ACTIVITY_NAME, "In onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(ACTIVITY_NAME, "In onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(ACTIVITY_NAME, "In onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACTIVITY_NAME, "In onDestroy()")
    }
}
