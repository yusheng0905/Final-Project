package com.ncueel.finalproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.squareup.picasso.Picasso

class CustomAdapterCart(private val context: Context, private val imageId: ArrayList<String>,
                        private val name: ArrayList<String>, private val price: ArrayList<String>,
                        private val number: ArrayList<String>, private val selNum: ArrayList<String>,
                        private val pId: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return imageId.size
    }

    override fun getItem(position: Int): Any {
        return imageId[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        val callDB = DBhelper(context)

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_items_cart, parent, false)
            viewHolder = ViewHolder()
            viewHolder.imageView = view.findViewById(R.id.imageView3)
            viewHolder.textView1 = view.findViewById(R.id.textView23)
            viewHolder.textView2 = view.findViewById(R.id.textView24)
            viewHolder.textView3 = view.findViewById(R.id.textView26)
            viewHolder.textView4 = view.findViewById(R.id.textView27)
            viewHolder.button = view.findViewById(R.id.button12)
            viewHolder.button2 = view.findViewById(R.id.button13)
            viewHolder.button3 = view.findViewById(R.id.button14)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.textView1?.text = name[position]
        viewHolder.textView2?.text = price[position]
        viewHolder.textView3?.text = number[position]
        viewHolder.textView4?.text = selNum[position]

        Picasso.get().load(imageId[position]).into(viewHolder.imageView)

        viewHolder.button?.setOnClickListener {
            if(selNum[position].toInt()>1) {
                selNum[position] = (selNum[position].toInt() - 1).toString()
                viewHolder.textView4?.text = selNum[position]
                callDB.searchCart(name[position], price[position], "-1", pId[position], imageId[position])
            }
        }

        viewHolder.button2?.setOnClickListener {
            if(selNum[position].toInt()<number[position].toInt()) {
                selNum[position] = (selNum[position].toInt() + 1).toString()
                viewHolder.textView4?.text = selNum[position]
                callDB.searchCart(name[position], price[position], "1", pId[position], imageId[position])
            }
        }

        viewHolder.button3?.setOnClickListener {
            callDB.deleteCart(pId[position])
            imageId.removeAt(position)
            name.removeAt(position)
            price.removeAt(position)
            number.removeAt(position)
            selNum.removeAt(position)
            pId.removeAt(position)
            notifyDataSetChanged()
        }

        return view
    }

    private class ViewHolder {
        var imageView: ImageView? = null
        var textView1: TextView? = null
        var textView2: TextView? = null
        var textView3: TextView? = null
        var textView4: TextView? = null
        var button: Button? = null
        var button2: Button? = null
        var button3: ImageButton? = null
    }
}
