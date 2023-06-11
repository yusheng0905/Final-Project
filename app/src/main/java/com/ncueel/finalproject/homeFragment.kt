package com.ncueel.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.SimpleAdapter
import android.widget.Toast
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

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
        val goodsNames = arrayOf("Itsukushima", "Kyoto", "Mount Fuji", "Okinawa", "Sensoji Temple")

        val goodsPrices = arrayOf("50", "51", "52", "53", "54")

        val goodsImageIds = arrayOf(R.drawable.itsukushima, R.drawable.kyoto, R.drawable.mount_fuji, R.drawable.okinawa, R.drawable.sensoji_temple)

        val gridView = view.findViewById<GridView>(R.id.gridView)
        val grid = ArrayList<HashMap<String, Any>>()
        for(i in goodsNames.indices) {
            val map = HashMap<String, Any>()
            map["goodsName"] = goodsNames[i]
            map["goodsPrice"] = "$${goodsPrices[i]}"
            map["goodsImage"] = goodsImageIds[i]
            grid.add(map)
        }
        val fromData = arrayOf("goodsName", "goodsPrice", "goodsImage")
        val toData = intArrayOf(R.id.textView6, R.id.textView10, R.id.imageView)
        val simpleAdapter = SimpleAdapter(requireContext(), grid, R.layout.grid_row_items_home, fromData, toData)
        gridView.adapter = simpleAdapter
        gridView.setOnItemClickListener { _, _, i, _ ->
            Toast.makeText(context, "${goodsNames[i]}",Toast.LENGTH_SHORT).show()
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