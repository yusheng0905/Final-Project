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

class CustomAdapterCheckout(private val context: Context, private val imageId: ArrayList<String>,
                            private val name: ArrayList<String>, private val price: ArrayList<String>,
                            private val selNum: ArrayList<String>) : BaseAdapter() {

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

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_items_checkout, parent, false)
            viewHolder = ViewHolder()
            viewHolder.imageView = view.findViewById(R.id.imageView4)
            viewHolder.textView1 = view.findViewById(R.id.textView35)
            viewHolder.textView2 = view.findViewById(R.id.textView36)
            viewHolder.textView3 = view.findViewById(R.id.textView37)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.textView1?.text = name[position]
        viewHolder.textView2?.text = "$${price[position]}"
        viewHolder.textView3?.text = "X ${selNum[position]}"

        Picasso.get().load(imageId[position]).into(viewHolder.imageView)

        return view
    }

    private class ViewHolder {
        var imageView: ImageView? = null
        var textView1: TextView? = null
        var textView2: TextView? = null
        var textView3: TextView? = null
    }
}
