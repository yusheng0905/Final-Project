package com.ncueel.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //清除重填
        val button = findViewById<Button>(R.id.button3)
        val nameEditText = findViewById<EditText>(R.id.name)
        button.setOnClickListener {nameEditText.setText("")}
//        val personIDEditText = findViewById<EditText>(R.id.personID)
//        button.setOnClickListener {personIDEditText.setText("")}
//        val phoneEditText = findViewById<EditText>(R.id.phone)
//        button.setOnClickListener {phoneEditText.setText("")}
//        val emailEditText = findViewById<EditText>(R.id.email)
//        button.setOnClickListener {emailEditText.setText("")}
//        val usernameEditText = findViewById<EditText>(R.id.username)
//        button.setOnClickListener {usernameEditText.setText("")}
    }
}