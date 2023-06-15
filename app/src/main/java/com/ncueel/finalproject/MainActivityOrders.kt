package com.ncueel.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ListView
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityOrders : AppCompatActivity() {
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_orders)

        val ListView1 = findViewById<ListView>(R.id.ListView1)
        val goodsNames = ArrayList<String>()
        val goodsPrices = ArrayList<String>()
        val goodsNumbers = ArrayList<String>()
        val goodsOid = ArrayList<String>()
        val goodsQuery = db.collection("test").document("order").collection("order")
        goodsQuery.get().addOnSuccessListener {
            for(oiddoc in it){
                val oid = oiddoc?.id.toString()
                goodsOid.add(oid)

//                val name = oiddoc?.get("name").toString()
//                val price = oiddoc?.get("price").toString()
//                val number = oiddoc?.get("number").toString()
//
//                goodsNames.add(name)
//                goodsPrices.add("$${price}")
//                goodsNumbers.add(number)
            }
            val orderAdapter = OrderAdapter(this, goodsOid)
            ListView1.adapter = orderAdapter
        }

//        for(i in goodsOid){
//            goodsQuery.document(i).collection(i).get().addOnSuccessListener {
//                for(item in it){
//                    val name = item?.get("name").toString()
//                    val price = item?.get("price").toString()
//                    val number = item?.get("number").toString()
//
//                    goodsNames.add(name)
//                    goodsPrices.add("$${price}")
//                    goodsNumbers.add(number)
//                }
//            }
//        }
//        goodsQuery.get().addOnSuccessListener{
//            val orderAdapter = OrderAdapter(this, goodsNames, goodsPrices, goodsNumbers,goodsOid)
//            ListView1.adapter = orderAdapter
        }
    }
