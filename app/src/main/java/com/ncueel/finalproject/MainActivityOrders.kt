package com.ncueel.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityOrders : AppCompatActivity() {
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_orders)
        val textForStore = findViewById<TextView>(R.id.textView8)
        val ListView1 = findViewById<ListView>(R.id.ListView1)
        val goodsNames = ArrayList<String>()
        val goodsPrices = ArrayList<String>()
        val goodsNumbers = ArrayList<String>()
        var goodsOid = ArrayList<String>()
        val totalAmount = ArrayList<String>()
        val goodsQuery = db.collection("test").document("order").collection("order")
        goodsQuery.get().addOnSuccessListener {
            for (oiddoc in it) {
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
            textForStore.text = goodsOid.toString()
        }
//        goodsOid.add("oid")
//        goodsOid.add("oid2")
//        goodsOid.add("oid3")

        goodsQuery.document("oid").collection("orderInfo").get().addOnSuccessListener {
            for (orderinfo in it) {
                val amount = orderinfo?.get("amount").toString()
                totalAmount.add(amount)
            }
            totalAmount.add("2")
            totalAmount.add("3")
            val orderAdapter = OrderAdapter(this, goodsOid,totalAmount)
            ListView1.adapter = orderAdapter
            ListView1.setOnItemClickListener { _, _, i, _ ->
                val dialogView = layoutInflater.inflate(R.layout.order_buttom_sheet, null)
                val dialog = BottomSheetDialog(this)

                dialog.setContentView(dialogView)

                dialog.show()
            }
        }



//        goodsQuery.get().addOnSuccessListener {
//
//        }

    }
}







