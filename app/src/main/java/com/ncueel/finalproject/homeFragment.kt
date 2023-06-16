package com.ncueel.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [homeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class homeFragment : Fragment() {
    private var db = Firebase.firestore
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ImageSlider
        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://fs-a.ecimg.tw/img/h24/v2/layout/index/hero/0647ef8629d69c647ef8629d6eeecman2/4_1_20230609144422.jpg"))
        imageList.add(SlideModel("https://fs-a.ecimg.tw/img/h24/v2/layout/index/hero/0647ef8629d69c647ef8629d6eeecman2/3_1_20230609144422.jpg"))
        imageList.add(SlideModel("https://fs-a.ecimg.tw/img/h24/v2/layout/index/hero/0647ef8629d69c647ef8629d6eeecman2/5_1_20230609144422.jpg"))
        imageList.add(SlideModel("https://fs-a.ecimg.tw/img/h24/v2/layout/index/hero/0647ef8629d69c647ef8629d6eeecman2/6_1_20230609144423.jpg"))
        imageList.add(SlideModel("https://fs-a.ecimg.tw/img/h24/v2/layout/index/hero/0647ef8629d69c647ef8629d6eeecman2/7_1_20230609112158.jpg"))
        imageList.add(SlideModel("https://fs-a.ecimg.tw/img/h24/v2/layout/index/hero/0647ef8629d69c647ef8629d6eeecman2/10_1_20230609112159.jpg"))
        //imageList.add(SlideModel(""))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)

        //gridView商品
        //連接資料庫讀取商品
        val goodsNames = ArrayList<String>()
        val goodsPrices = ArrayList<String>()
        val goodsNumbers = ArrayList<String>()
        val goodsImageIds = ArrayList<String>()
        val goodsPIds = ArrayList<String>()
        val gridView = view.findViewById<GridView>(R.id.gridView)
        val goodsQuery = db.collection("goods")

        goodsQuery.get().addOnSuccessListener {
            for(goodsDoc in it) {
                val name = goodsDoc?.get("name").toString()
                val price = goodsDoc?.get("price").toString()
                val number = goodsDoc?.get("number").toString()
                val pictureUrl = goodsDoc?.get("pictureUrl").toString()
                val productId = goodsDoc?.id.toString()

                goodsNames.add(name)
                goodsPrices.add(price)
                goodsNumbers.add(number)
                goodsImageIds.add(pictureUrl)
                goodsPIds.add(productId)
            }

            val customAdapter = CustomAdapter(requireContext(), goodsNames, goodsPrices, goodsImageIds)
            gridView.adapter = customAdapter
            gridView.setOnItemClickListener { _, _, i, _ ->
                //Toast.makeText(context, "${goodsPIds[i]}",Toast.LENGTH_SHORT).show()
                //顯示所選擇商品的資訊
                val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
                val dialog = BottomSheetDialog(requireContext())

                dialog.setContentView(dialogView)
                Picasso.get().load(goodsImageIds[i]).into(dialog.findViewById(R.id.imageView2))
                dialog.findViewById<TextView>(R.id.textView12)?.text = goodsNames[i]
                dialog.findViewById<TextView>(R.id.textView13)?.text = "$${goodsPrices[i]}"
                dialog.findViewById<TextView>(R.id.textView15)?.text = goodsNumbers[i]

                val selSub = dialog.findViewById<Button>(R.id.button8)
                val selAdd = dialog.findViewById<Button>(R.id.button9)
                val addToCart = dialog.findViewById<Button>(R.id.button10)
                var selNum = dialog.findViewById<TextView>(R.id.textView16)?.text
                var selNumm = selNum.toString().toInt()

                dialog.show()

                selSub?.setOnClickListener{
                    if(selNumm > 1) {
                        selNumm -= 1
                        dialog.findViewById<TextView>(R.id.textView16)?.text = "${selNumm}"
                    }
                    else dialog.findViewById<TextView>(R.id.textView16)?.text = "${selNumm}"
                }

                selAdd?.setOnClickListener{
                    if(selNumm < goodsNumbers[i].toInt()) {
                        selNumm += 1
                        dialog.findViewById<TextView>(R.id.textView16)?.text = "${selNumm}"
                    }
                    else dialog.findViewById<TextView>(R.id.textView16)?.text = "${selNumm}"
                }

                if (goodsNumbers[i].toInt() == 0){
                    addToCart?.isEnabled = false
                    addToCart?.text = "缺貨中"
                }

                addToCart?.setOnClickListener {
                    val callDB = DBhelper(requireContext())
                    callDB.searchCart(goodsNames[i], goodsPrices[i], selNumm.toString(), goodsPIds[i], goodsImageIds[i])
                }
            }
        }
        .addOnFailureListener {
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment homeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            homeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}