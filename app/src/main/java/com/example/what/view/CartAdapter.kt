package com.example.what.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.what.R
import com.example.what.model.Cart
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

class CartAdapter(private val context: Context, private var itemList: MutableList<Cart>, private val clickListener: ItemClickListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    interface ItemClickListener {
        fun minusClick(position: Int)
        fun plusClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val qtyTextView: TextView = itemView.findViewById(R.id.textQty)
        val minus: ImageButton = itemView.findViewById(R.id.minus)
        val plus: ImageButton = itemView.findViewById(R.id.plus)
        init {
            minus.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    qtyTextView.text = (qtyTextView.text.toString().toInt()-1).toString()
                    clickListener.minusClick(position)
                }
            }
            plus.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    qtyTextView.text = (qtyTextView.text.toString().toInt()+1).toString()

                    clickListener.plusClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        val imageRequest = ImageRequest(
            item.menu.img,
            Response.Listener { response ->
                holder.imageView.setImageBitmap(response)
            },
            0,
            0,
            ImageView.ScaleType.CENTER_INSIDE,
            null,
            Response.ErrorListener { error ->
                // Handle error
            }
        )

        Volley.newRequestQueue(context).add(imageRequest)
        holder.qtyTextView.text = item.qty.toString()
        holder.titleTextView.text = item.menu.name
        holder.priceTextView.text = "RP. "+item.menu.price.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}