package com.ncueel.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ncueel.finalproject.databinding.ActivityMainOrderSuccessBinding

class MainActivityOrderSuccess : AppCompatActivity() {
    private lateinit var binding: ActivityMainOrderSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button55.setOnClickListener {
            startActivity(Intent(this, MainActivity_Home::class.java))
        }
    }

    // Disable back button
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }
}