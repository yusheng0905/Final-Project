package com.ncueel.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //跳到註冊頁面
        val signPage = findViewById<Button>(R.id.button2)
        signPage.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }
    }
}