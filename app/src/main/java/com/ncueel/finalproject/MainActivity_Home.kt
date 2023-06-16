package com.ncueel.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.ncueel.finalproject.databinding.ActivityMainHomeBinding

class MainActivity_Home : AppCompatActivity() {

    private lateinit var binding: ActivityMainHomeBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.activity_home_nav_fragment)
        setupWithNavController(binding.bottomNavigationView, navController)
    }
}