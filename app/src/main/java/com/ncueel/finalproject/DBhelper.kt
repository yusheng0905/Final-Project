package com.ncueel.finalproject

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DBhelper(private val context: Context) {
    //取得Cloud Firestore物件
    private val db = Firebase.firestore
    private val UserId = FirebaseAuth.getInstance().currentUser!!.uid

    fun searchCart(name: String, price: String, number: String, pId: String, imageId: String){
        db.collection(UserId).document("cart").collection("cartInfo")
            .get().addOnSuccessListener {
                var chkVar = false
                for (document in it) {
                    if (pId == document.id) {
                        chkVar = true
                        val oriNum = document?.get("number").toString()
                        addCart(oriNum, number, pId)
                    }
                }
                if (chkVar) {}
                else saveCart(name, price, number, pId, imageId)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveCart(name: String, price: String, number: String, pId: String, imageId: String) {

        val goodsMap = hashMapOf(
            "name" to name,
            "price" to price,
            "number" to number,
            "pictureUrl" to imageId
        )

        db.collection(UserId).document("cart").collection("cartInfo").document(pId).set(goodsMap)
            .addOnSuccessListener {
                Toast.makeText(context, "已加入購物車", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "操作失敗", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addCart(oriNum:String, number:String, pId:String){
        var updateMap = mutableMapOf<String,Any>()

        updateMap = hashMapOf("number" to (number.toInt() + oriNum.toInt()).toString())

        db.collection(UserId).document("cart").collection("cartInfo").document(pId).update(updateMap)
            .addOnSuccessListener{
                //Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "操作失敗", Toast.LENGTH_SHORT).show()
            }
    }

    fun refreshCart(number:String, pId:String){
        var updateMap = mutableMapOf<String,Any>()

        updateMap = hashMapOf("number" to number)

        db.collection(UserId).document("cart").collection("cartInfo").document(pId).update(updateMap)
            .addOnSuccessListener{
                //Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "操作失敗", Toast.LENGTH_SHORT).show()
            }
    }

    fun deleteCart(pId:String){
        db.collection(UserId).document("cart")
            .collection("cartInfo").document(pId)
            .delete()
            .addOnSuccessListener{
                //Toast.makeText(context, "刪除成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun saveOrderInfo(time: String, userName: String, phone: String, address: String, amount: String, delivery: String, payment: String) {

        val oId = (Integer.parseInt(time)).toString()
        val goodsMap = hashMapOf(
            "time" to time,
            "userName" to userName,
            "phone" to phone,
            "address" to address,
            "amount" to amount,
            "delivery" to delivery,
            "payment" to payment
        )

        db.collection(UserId).document("order").collection("order").document(oId)
            .collection("orderInfo").document("orderInfo").set(goodsMap).addOnSuccessListener {
                Toast.makeText(context, "已送出訂單", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun saveOrderGoods(time: String, pId: String, name: String, selNum: String, price: String, imageUrl: String) {

        val oId = (Integer.parseInt(time)).toString()
        val goodsMap = hashMapOf(
            "time" to time,
            "pId" to pId,
            "name" to name,
            "selNum" to selNum,
            "price" to price,
            "imageUrl" to imageUrl
        )

        db.collection(UserId).document("order").collection("order").document(oId)
            .collection("orderContent").document(pId).set(goodsMap).addOnSuccessListener {
                //Toast.makeText(context, "已送出訂單", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun refreshGoods(pId: ArrayList<String>, selNum: ArrayList<String>){
        var updateMap = mutableMapOf<String,Any>()
        val goodsNumbers = ArrayList<String>()

        //更新可買數量
        db.collection("goods").get().addOnSuccessListener {
            for (goodsDoc in it) {
                val number = goodsDoc?.get("number").toString()
                val productId = goodsDoc?.id.toString()

                for (i in pId.indices) {
                    if (productId == pId[i]) {
                        goodsNumbers.add(number)
                        updateMap = hashMapOf("number" to (number.toInt() - selNum[i].toInt()).toString())

                        db.collection("goods").document(pId[i]).update(updateMap)
                            .addOnSuccessListener{
                                //Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "操作失敗", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
        }
    }

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
                Toast.makeText(context, "successfully update", Toast.LENGTH_SHORT).show()
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
