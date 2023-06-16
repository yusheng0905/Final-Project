package com.ncueel.finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class OrderAdapter(private val context: Context, private val data4: ArrayList<String>,private val data5: ArrayList<String>):BaseAdapter(){
    override fun getCount(): Int {
        return data4.size
    }

    override fun getItem(position: Int): Any {
        return data4[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View  {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_items_orders, parent, false)
            viewHolder = ViewHolder()
            viewHolder.textView1 = view.findViewById(R.id.textView20)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
//        val item = data[position]
//        val item2 = data2[position]
//        val item3 = data3[position]
        val item4 = data4[position]
        val item5 = data5[position]

        viewHolder.textView1?.text = item4+"總金額："+item5

        return view
    }

    private class ViewHolder {
        var textView1: TextView? = null
    }
}