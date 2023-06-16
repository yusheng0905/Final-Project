package com.ncueel.finalproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityOrders : AppCompatActivity() {
    private var db = Firebase.firestore
    private val UserId = FirebaseAuth.getInstance().currentUser!!.uid
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_orders)
        val ListView1 = findViewById<ListView>(R.id.ListView1)

        var orderOid = ArrayList<String>()
        val orderQuery = db.collection(UserId).document("order").collection("order")
        orderQuery.get().addOnSuccessListener {
            for (orderdoc in it) {
                val oid = orderdoc?.id.toString()
                orderOid.add(oid)

            }
            Toast.makeText(this,"${orderOid}",Toast.LENGTH_SHORT).show()
            val orderAdapter = OrderAdapter(this, orderOid)
            ListView1.adapter = orderAdapter
            ListView1.setOnItemClickListener { _, _, i, _ ->
                val dialogView = layoutInflater.inflate(R.layout.order_buttom_sheet, null)
                val dialog = BottomSheetDialog(this)

                dialog.setContentView(dialogView)
                val orderInfomation = dialog.findViewById<TextView>(R.id.textView40)
                orderQuery.document(orderOid[i]).collection("orderInfo").get().addOnSuccessListener{inner->
                    for(info in inner){
                        val address = info?.get("address").toString()
                        val amount = info?.get("amount").toString()
                        val delivery = info?.get("delivery").toString()
                        val payment = info?.get("payment").toString()
                        val phone = info?.get("phone").toString()
                        val userName = info?.get("userName").toString()
                        val time = info?.get("time").toString()

                        orderInfomation?.text = "\n"+
                                                "訂購人："+userName+"\n"+
                                                "聯絡電話："+phone+"\n"+
                                                "地址："+address+"\n"+
                                                "運送方式："+delivery+"\n"+
                                                "付款方式："+payment+"\n"+
                                                "訂購時間："+time+"\n\n"+
                                                "總金額： NT$"+amount+"\n"
                    }
                }

                dialog.show()
        }

//        goodsQuery.document("oid").collection("orderInfo").get().addOnSuccessListener {
//            for (orderinfo in it) {
//                val amount = orderinfo?.get("amount").toString()
//                totalAmount.add(amount)
//            }
//            totalAmount.add("2")
//            totalAmount.add("3")
//
//            }
        }





    }
}







