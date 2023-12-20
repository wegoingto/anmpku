package com.example.what.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.what.R
import com.example.what.databinding.FragmentOrderBinding
import com.example.what.model.Order
import com.example.what.viewmodel.OrderViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.NumberFormat
import java.util.Locale


class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreferences = requireContext().getSharedPreferences("WHAT", Context.MODE_PRIVATE)
        val orderViewModel =
            ViewModelProvider(this).get(OrderViewModel::class.java)
        val tbl = arguments?.getInt("table")?.toInt() ?: 0

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val order = sharedPreferences.getString("orders", "[]")
        if(order == "[]"){
            root.findViewById<LinearLayout>(R.id.noOrders).visibility = View.VISIBLE
        }
        else{
            val gson = Gson()
            val orderListType = object : TypeToken<List<Order>>() {}.type
            root.findViewById<TextView>(R.id.noOrders).visibility = View.GONE
            var orders = gson.fromJson<MutableList<Order>>(order, orderListType)
            val adapterOrders = OrderAdapter(requireContext(), orders)
            val orderView = root.findViewById<RecyclerView>(R.id.orderList)
            orderView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            orderView.adapter = adapterOrders
        }
        return root
    }
    fun formatNumber(number: Int): String{
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }
    fun newInstance(table: Int): MenuFragment? {
        val fragment = MenuFragment()
        val args = Bundle()
        args.putInt("table", table)
        fragment.arguments = args
        return fragment
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}