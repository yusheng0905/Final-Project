package com.ncueel.finalproject

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DBhelper(private val context: Context) {
    //取得Cloud Firestore物件
    val db = Firebase.firestore

    fun save(name: EditText, price: EditText, number: EditText) {
        //刪除前後空格 trim()
        val sName = name.text.toString().trim()
        val sPrice = price.text.toString().trim()
        val sNumber = number.text.toString().trim()

        val goodsMap = hashMapOf(
            "name" to sName,
            "price" to sPrice,
            "number" to sNumber
        )

        db.collection("test").document("0001").set(goodsMap)
            .addOnSuccessListener {
                Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show()
                name.text.clear()
                price.text.clear()
                number.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private lateinit var getdbname:String
    private lateinit var getdbprice:String
    private lateinit var getdbnumber:String
    fun getdb(type: Int) :String{
        // type  1=name,2=price,3=number
        val getRef = db.collection("test").document("0001")
        getRef.get().addOnSuccessListener  {
                if (it!=null) {
                    val dbname = it.data?.get("name").toString()
                    val dbprice = it.data?.get("price").toString()
                    val dbnumber = it.data?.get("number").toString()

                    getdbname = dbname
                    getdbprice = dbprice
                    getdbnumber = dbnumber

                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
        var a = getRef.get().addOnSuccessListener {
            if (it != null) {
                it.data?.getValue("name").toString()
            }
        }

        return when (type) {
            1 -> {
                getdbname
            }
            2 -> {
                getdbprice
            }
            3 -> {
                getdbnumber
            }
            else -> "not found"
        }
    }
}
