package com.example.what.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.what.R
import com.example.what.model.Menu
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


class MenuAdapter(private val context: Context, private var itemList: List<Menu>, private val onItemClick: (Menu) -> Unit) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    fun updateData(newData: List<Menu>) {
        itemList = newData
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        val imageRequest = ImageRequest(
            item.img,
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
        holder.titleTextView.text = item.name
        holder.priceTextView.text = "RP. "+item.price.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}