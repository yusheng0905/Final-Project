package com.ncueel.finalproject

import android.content.Context
import android.widget.EditText
import android.widget.TextView
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


    fun update(up_type:String,up_object:EditText,doc_id:String){
        lateinit var upName:String
        lateinit var upPrice:String
        lateinit var upNumber:String
        var updateMap = mutableMapOf<String,Any>()
        if (up_type=="name"){
            upName = up_object.text.toString().trim()
            updateMap = hashMapOf("name" to upName)
        }else if(up_type=="price"){
            upPrice = up_object.text.toString().trim()
            updateMap = hashMapOf("price" to upPrice)
        }else if(up_type=="number"){
            upNumber = up_object.text.toString().trim()
            updateMap = hashMapOf("number" to upNumber)
        }else{
            val error = "up_type error."
        }
//        val updateMap = hashMapOf(
//            "name" to upName,
//            "price" to upPrice,
//            "number" to upNumber
//        )
        db.collection("test").document(doc_id).update(updateMap)
            .addOnSuccessListener{
                Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show()
                up_object.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
    }
//    private lateinit var getdbname:TextView
//    private lateinit var getdbprice:TextView
//    private lateinit var getdbnumber:TextView
//    private lateinit var notfound:TextView
//    fun getdb(type: Int):TextView {
//        // type  1=name,2=price,3=number
//        val getRef = db.collection("test").document("0001")
//        getRef.get().addOnSuccessListener  {
//               if (it!=null) {
//                   val dbname = it.data?.get("name").toString()
//                    val dbprice = it.data?.get("price").toString()
//                    val dbnumber = it.data?.get("number").toString()
//
//                   getdbname.text = dbname
//                   getdbprice.text = dbprice
//                   getdbnumber.text = dbnumber
//                   notfound.text = "not found"
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//            }
//
//        return when (type) {
//            1 -> {
//                getdbname
//            }
//            2 -> {
//                getdbprice
//            }
//            3 -> {
//                getdbnumber
//            }
//            else -> notfound
//        }
//    }
}
