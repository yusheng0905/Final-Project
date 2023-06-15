package com.ncueel.finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CustomAdapter(private val context: Context, private val data: ArrayList<String>, private val data2: ArrayList<String>, private val data3: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_row_items_home, parent, false)
            viewHolder = ViewHolder()
            viewHolder.imageView = view.findViewById(R.id.imageView)
            viewHolder.textView1 = view.findViewById(R.id.textView6)
            viewHolder.textView2 = view.findViewById(R.id.textView10)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val item = data[position]
        val item2 = data2[position]
        val item3 = data3[position]

        viewHolder.textView1?.text = item
        viewHolder.textView2?.text = item2

        Picasso.get()
            .load(item3)
            .into(viewHolder.imageView)

        return view
    }

    class ViewHolder {
        var imageView: ImageView? = null
        var textView1: TextView? = null
        var textView2: TextView? = null
    }
}
