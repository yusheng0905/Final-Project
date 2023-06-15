package com.ncueel.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.ncueel.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Step 1: 初始化FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Step 1:先從註冊這個功能開始寫->先初始化一個資料鏈結的部分
    private lateinit var binding: ActivityMainBinding

    //save variable
    private lateinit var name:EditText
    private lateinit var price:EditText
    private lateinit var number:EditText
    private lateinit var savebtn: Button

    //get variable
    lateinit var getname:TextView
    private lateinit var getprice:TextView
    private lateinit var getnumber:TextView
    private lateinit var getbtn: Button


    //update variable
//    private lateinit var upname:EditText
//    private lateinit var upprice:EditText
//    private lateinit var upnumber:EditText
    private lateinit var upobject:EditText
    private lateinit var updatebtn:Button

    //Retrieve Image
    private lateinit var getimage:ImageView
    private lateinit var databaseReference: DatabaseReference

    //取得Cloud Firestore物件
    private var db = Firebase.firestore

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

        //save items
        name = findViewById(R.id.editTextText2)
        price = findViewById(R.id.editTextText3)
        number = findViewById(R.id.editTextText4)
        savebtn = findViewById(R.id.button5)

        //get items
        getname = findViewById(R.id.textView7)
        getnumber = findViewById(R.id.textView8)
        getprice = findViewById(R.id.textView9)
        getbtn = findViewById(R.id.button6)


        //呼叫class DBhelper,連接資料庫 儲存資料
        savebtn.setOnClickListener {
            val aa = DBhelper(this)
            aa.save(name,price,number)

            //刪除前後空格
//            val sName = name.text.toString().trim()
//            val sPrice = price.text.toString().trim()
//            val sNumber = number.text.toString().trim()

//            val goodsMap = hashMapOf(
//                "name" to sName,
//                "price" to sPrice,
//                "number" to sNumber
//            )
            //get uid
            //val goodsId = FirebaseAuth.getInstance().currentUser!!.uid

//            db.collection("goods").document("0001").set(goodsMap)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "successfully added", Toast.LENGTH_SHORT).show()
//                    name.text.clear()
//                    price.text.clear()
//                    number.text.clear()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
//                }
        }

        //

        getbtn.setOnClickListener {
            val bb = DBhelper(this)
            //getdb(type)  type:1=name,2=price,3=number
//            bb.getdb(1)
//            getprice.text = bb.get(2)
//            getnumber.text = bb.get(3)

//            val getRef = db.collection("test").document("0001")
//            getRef.get().addOnSuccessListener {
//                if (it != null) {
//                    val name = it.data?.get("name").toString()
//                    val price = it.data?.get("price").toString()
//                    val number = it.data?.get("number").toString()
//
//                    getname.text = name
//                    getprice.text = price
//                    getnumber.text = number
//                }
//            }
//                .addOnFailureListener {
//                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
//                }
        }

        //update items
        upobject = findViewById(R.id.editTextText5)
        updatebtn = findViewById(R.id.button7)

        updatebtn.setOnClickListener {
            val up = DBhelper(this)
            up.update("number",upobject,"0001")
        }


        //display image from db
//        getimage = findViewById(R.id.imageView2)
//        databaseReference = FirebaseDatabase.getInstance().getReference("userImages")
//            .child()

//        var textView11 = findViewById<TextView>(R.id.textView11)
//        val button7 = findViewById<Button>(R.id.button7)
//
//        val dbdb = DBhelper()
//        button7.setOnClickListener {
//            textView11.text = dbdb.save("adam").toString()
//        }
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