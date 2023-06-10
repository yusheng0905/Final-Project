package com.ncueel.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseUser
import com.ncueel.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Step 1: 初始化FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Step 1:先從註冊這個功能開始寫->先初始化一個資料鏈結的部分
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //** Step 2: 去設定我們要去啟動的RegisterActivity **
        //Step 2-1: 初始化FirebaseAuth->初始化auth
        auth = Firebase.auth

        //Step 2-2:先從註冊這個功能開始寫->初始化binding要鏈結的layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //跳到註冊頁面
        binding.button2.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }

        //Step 3-1: 設計登入的button
        binding.button.setOnClickListener {
            if (binding.editTextText.text.toString().isEmpty()) {
                showMessage("請輸入帳號")
            } else if (binding.editTextTextPassword.text.toString().isEmpty()) {
                showMessage("請輸入密碼")
            } else {
                signIn()
            }
        }
    }
    //Step 3-2: 設計登入的button->寫一個登入認證失敗的判斷式
    private fun signIn() {
        val email = binding.editTextText.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //Log.d("signInWithEmail:success")
                    println("---------signInWithEmail:success-----------")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    it.exception?.message?.let {  }
                    println("---------error---------------")
                    showMessage("登入失敗，帳號或密碼錯誤")
                    updateUI(null)
                }
            }
        startActivity(Intent(this,MainActivity_Home::class.java))
    }

    //Step 6: 確認更新登入的user狀況
    private fun updateUI(user: FirebaseUser?) {
        if ( user!= null){
            //已登入
            binding.editTextText.visibility = View.GONE
            binding.editTextTextPassword.visibility = View.GONE
            binding.button.visibility = View.GONE
            binding.button2.visibility = View.GONE


        }else{
            //未登入
            binding.editTextText.visibility = View.VISIBLE
            binding.editTextTextPassword.visibility = View.VISIBLE
            binding.button.visibility = View.VISIBLE
            binding.button2.visibility = View.VISIBLE

        }
    }

    //Step 3-3: 設計登入的button->設計一個給使用者確認的message
    private fun showMessage(message: String) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("確定") { dialog, which -> }
        alertDialog.show()
    }
}