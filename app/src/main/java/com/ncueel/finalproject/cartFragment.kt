package com.ncueel.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [cartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class cartFragment : Fragment() {
    private var db = Firebase.firestore
    private val UserId = FirebaseAuth.getInstance().currentUser!!.uid
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callDB = DBhelper(requireContext())

        val goodsNames = ArrayList<String>()
        val goodsPrices = ArrayList<String>()
        val goodsSelNums = ArrayList<String>()
        val goodsImageIds = ArrayList<String>()
        val goodsPIds = ArrayList<String>()
        val goodsNumbers = ArrayList<String>()
        val listView = view.findViewById<ListView>(R.id.listView)
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
            }

        //更新可買數量
        db.collection("goods").get().addOnSuccessListener {
            for(goodsDoc in it) {
                val number = goodsDoc?.get("number").toString()
                val productId = goodsDoc?.id.toString()

                for(i in goodsNames.indices){
                    if(productId == goodsPIds[i]){
                        goodsNumbers.add(number)
                    }
                }
            }
            for(i in goodsSelNums.indices){
                if(goodsSelNums[i].toInt() > goodsNumbers[i].toInt()) goodsSelNums[i] = goodsNumbers[i]
                callDB.refreshCart(goodsSelNums[i], goodsPIds[i])
            }
            val customAdapter = CustomAdapterCart(requireContext(), goodsImageIds, goodsNames, goodsPrices, goodsNumbers, goodsSelNums, goodsPIds)
            listView.adapter = customAdapter
            val goCheckout = view.findViewById<Button>(R.id.button16)
            goCheckout.setOnClickListener {
                if(goodsPIds.isEmpty()) {
                    Toast.makeText(context, "購物車是空的!", Toast.LENGTH_SHORT).show()
                }
                else{
                    //Toast.makeText(context, "goCheckout!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, MainActivityCheckout::class.java))
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment cartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            cartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}