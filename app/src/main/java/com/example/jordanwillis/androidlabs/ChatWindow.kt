package com.example.jordanwillis.androidlabs

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*

class ChatWindow : Activity() {

    private var messages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ChatWindow", "onCreate() called for ChatWindow")
        setContentView(R.layout.activity_chat_window)

        val dbHelper = MyOpenHelper()
        val db = dbHelper.writableDatabase
        val result = db.query(TABLE_NAME, arrayOf("_id", KEY_MESSAGES), null, null, null, null, null, null)
        val numRows = result.count
        result.moveToFirst() // Point to first row
        val idIndex = result.getColumnIndex("_id") // Find index of _id column
        val messagesIndex = result.getColumnIndex(KEY_MESSAGES) // Find index of messages column
        while(!result.isAfterLast) { // While not reading data
            var thisId = result.getInt(idIndex)
            var thisMessage = result.getString(messagesIndex)
            Log.d("Message Retrieved: ", "ID: $thisId, Message: $thisMessage")
            messages.add(thisMessage)
            result.moveToNext() // Look at next row of data
        }

        var inputText = findViewById<EditText>(R.id.inputText)
        var button = findViewById<Button>(R.id.sendChat)
        var listItem = findViewById<ListView>(R.id.list_item)

        var messageAdapter = ChatAdapter(this)

         button.setOnClickListener() {
             var context = this
             var userTyped = inputText.text.toString()
             messages.add(userTyped)
             // Write to database
             val newRow = ContentValues()
             newRow.put(KEY_MESSAGES, userTyped)
             // Add new data to database
             db.insert(TABLE_NAME, "", newRow)

             messageAdapter.notifyDataSetChanged()
             inputText.setText("")
             val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
             imm.hideSoftInputFromWindow(button.windowToken, 0)
         }
        listItem.adapter = messageAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        MyOpenHelper().close()
    }

    inner class ChatAdapter(ctx: Context) : ArrayAdapter<String>(ctx, 0) {

        override fun getCount(): Int {
            return messages.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var inflater = LayoutInflater.from(parent.getContext())
            var result: View

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null)
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null)
            }

            val message = result.findViewById(R.id.message_text) as TextView
            message.setText(getItem(position)) // get the string at position
            return result
        }
        override fun getItem(position: Int): String? {
            return messages.get(position)
        }
        override fun getItemId(position: Int): Long {
            return 0
        }
    }

    val DATABASE_NAME = "MessagesDatabase"
    val VERSION_NUMBER = 2
    val TABLE_NAME = "Messages"
    val KEY_MESSAGES = "Messages"

    inner class MyOpenHelper : SQLiteOpenHelper(this@ChatWindow, DATABASE_NAME, null, VERSION_NUMBER) {
        override fun onCreate(db: SQLiteDatabase) {
            Log.d("MyOpenHelper", "Calling onCreate for SQLiteOpenHelper")
            db.execSQL("CREATE TABLE $TABLE_NAME ( _id INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_MESSAGES TEXT) ")
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            val oldVersion = VERSION_NUMBER - 1
            val newVersion = VERSION_NUMBER
            // Delete current table
            Log.d("MyOpenHelper", "Calling onUpgrade, oldVersion=$oldVersion newVersion=$newVersion")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            // Create new table
            onCreate(db)
        }

    }
}
