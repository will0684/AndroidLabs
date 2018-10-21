package com.example.jordanwillis.androidlabs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class LoginActivity : Activity() {

    val ACTIVITY_NAME = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.i(ACTIVITY_NAME, "In onCreate()")

        val sharedPreferences = getSharedPreferences("email", Context.MODE_PRIVATE)
        val emailPref = sharedPreferences.getString("DefaultEmail", "email@domain.com")
        val emailField = findViewById<EditText>(R.id.email_field)
        emailField.setText(emailPref)

        val button = findViewById<Button>(R.id.login_button_id)
        button.setOnClickListener {
            val email = emailField.getText().toString()
            sharedPreferences.edit().putString("DefaultEmail", email).commit()
            val intent = Intent(this@LoginActivity, StartActivity::class.java)
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
