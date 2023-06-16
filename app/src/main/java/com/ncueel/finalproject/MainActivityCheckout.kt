package com.ncueel.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ncueel.finalproject.databinding.ActivityMainCheckoutBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivityCheckout : AppCompatActivity() {
    private lateinit var binding: ActivityMainCheckoutBinding
    private var db = Firebase.firestore
    private val UserId = FirebaseAuth.getInstance().currentUser!!.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val goodsNames = ArrayList<String>()
        val goodsPrices = ArrayList<String>()
        val goodsSelNums = ArrayList<String>()
        val goodsImageIds = ArrayList<String>()
        val goodsPIds = ArrayList<String>()
        val listView = binding.ListView3
        val callDB = DBhelper(this)
        //get用戶購物車資料
        db.collection(UserId).document("cart").collection("cartInfo")
            .get().addOnSuccessListener {
                for(goodsDoc in it) {
                    val name = goodsDoc?.get("name").toString()
                    val price = goodsDoc?.get("price").toString()
                    val selNum = goodsDoc?.get("number").toString()
                    val pictureUrl = goodsDoc?.get("pictureUrl").toString()
                    val productId = goodsDoc?.id.toString()

                    goodsNames.add(name)
                    goodsPrices.add(price)
                    goodsSelNums.add(selNum)
                    goodsImageIds.add(pictureUrl)
                    goodsPIds.add(productId)
                }
                val customAdapter = CustomAdapterCheckout(this, goodsImageIds, goodsNames, goodsPrices, goodsSelNums)
                listView.adapter = customAdapter
                ListViewUtils.setListViewHeightBasedOnItems(listView)

                var amount = 0
                for(i in goodsPrices.indices){
                    amount += goodsPrices[i].toInt() * goodsSelNums[i].toInt()
                }

                //顯示總金額
                binding.textView29.text = "總金額:${amount}元 "

                //選擇付款方式
                val payList = arrayOf("信用卡", "轉帳", "貨到付款")
                var payPos = 0
                var payment = String()
                binding.button16.setOnClickListener {
                    AlertDialog.Builder(this).setTitle("請選擇付款方式")
                        .setSingleChoiceItems(payList, -1) { dialogInterface, i ->
                            payPos = i
                        }
                        .setPositiveButton("確定") { dialog, which ->
                            //Toast.makeText(this,"你選的是${payList[payPos]}", Toast.LENGTH_LONG).show()
                            payment = payList[payPos]
                            binding.button16.text = "${payment}"
                        }.show()
                }

                //選擇寄送方式
                val delList = arrayOf("宅配", "7-11取貨", "全家取貨")
                var delPos = 0
                var delivery = String()
                binding.button17.setOnClickListener {
                    AlertDialog.Builder(this).setTitle("請選擇寄送方式")
                        .setSingleChoiceItems(delList, -1) { dialogInterface, i ->
                            delPos = i
                        }
                        .setPositiveButton("確定") { dialog, which ->
                            //Toast.makeText(this,"你選的是${delList[delPos]}", Toast.LENGTH_LONG).show()
                            delivery = delList[delPos]
                            binding.button17.text = "${delivery}"
                        }.show()
                }

                //送出訂單
                binding.button15.setOnClickListener {
                    val userName = binding.editTextText2.text.toString()
                    val phone = binding.editTextText3.text.toString()
                    val address = binding.editTextText4.text.toString()
                    if(userName == "") Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show()
                    else if(phone == "") Toast.makeText(this, "請輸入電話", Toast.LENGTH_SHORT).show()
                    else if(address == "") Toast.makeText(this, "請輸入地址", Toast.LENGTH_SHORT).show()
                    else if(payment == "") Toast.makeText(this, "請選擇付款方式", Toast.LENGTH_SHORT).show()
                    else if(delivery == "") Toast.makeText(this, "請選擇寄送方式", Toast.LENGTH_SHORT).show()
                    else{
                        val currentTime = Date()
                        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()) // 設定時間的格式
                        val oIdFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
                        val formattedTime = dateFormat.format(currentTime) // 將時間格式化成字串
                        val oId = oIdFormat.format(currentTime)
                        callDB.saveOrderInfo(oId, formattedTime, userName, phone, address, amount.toString(), delivery, payment)
                        for(i in goodsPIds.indices) {
                            callDB.saveOrderGoods(oId, formattedTime, goodsPIds[i], goodsNames[i], goodsSelNums[i], goodsPrices[i], goodsImageIds[i])
                            callDB.deleteCart(goodsPIds[i])
                        }
                        callDB.refreshGoods(goodsPIds, goodsSelNums)
                        startActivity(Intent(this, MainActivityOrders::class.java))
                    }
                }
            }
    }

    object ListViewUtils {
        fun setListViewHeightBasedOnItems(listView: ListView) {
            val listAdapter = listView.adapter ?: return
            val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
            var totalHeight = 0
            for (i in 0 until listAdapter.count) {
                val listItem = listAdapter.getView(i, null, listView)
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
            }
            val params = listView.layoutParams
            params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
            listView.layoutParams = params
            listView.requestLayout()
        }
    }
}