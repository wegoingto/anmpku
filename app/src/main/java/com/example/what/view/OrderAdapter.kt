package com.example.what.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.what.R
import com.example.what.model.Order
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(private val context: Context, private var itemList: MutableList<Order>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tbl: TextView = itemView.findViewById(R.id.tbl)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_orders, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        var total = 0
        for(cart in item.cart){
            total += cart.menu.price*cart.qty
        }
        total = total + (total*10/100)
        holder.tbl.text = item.tbl.toString()
        holder.priceTextView.text = "IDR. "+formatNumber(total)
    }
    fun formatNumber(number: Int): String{
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
}