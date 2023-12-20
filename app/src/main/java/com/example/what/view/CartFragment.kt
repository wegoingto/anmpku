package com.example.what.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.what.R
import com.example.what.databinding.FragmentCartBinding
import com.example.what.model.Cart
import com.example.what.model.Order
import com.example.what.viewmodel.CartViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.NumberFormat
import java.util.Locale


class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreferences = requireContext().getSharedPreferences("WHAT", Context.MODE_PRIVATE)
        val cartViewModel =
            ViewModelProvider(this).get(CartViewModel::class.java)
        val tbl = arguments?.getInt("table")?.toInt() ?: 0

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val cart = sharedPreferences.getString("cart", "[]")
        if(cart == "[]"){
            root.findViewById<LinearLayout>(R.id.previewPrice).visibility = View.GONE

        }
        else{
            var subtotal = 0;
            val gson = Gson()
            val cartListType = object : TypeToken<List<Cart>>() {}.type
            var carts = gson.fromJson<MutableList<Cart>>(cart, cartListType)
            root.findViewById<TextView>(R.id.noItem).visibility = View.GONE
            root.findViewById<LinearLayout>(R.id.previewPrice).visibility = View.VISIBLE
            val btn = root.findViewById<Button>(R.id.checkout)
            btn.visibility = View.VISIBLE
            btn.setOnClickListener {
                val orderStr = sharedPreferences.getString("orders", "[]")
                val gson = Gson()
                val orderListType = object : TypeToken<List<Order>>() {}.type
                var orders = gson.fromJson<MutableList<Order>>(orderStr, orderListType)
                val order = Order(carts, tbl)
                orders.add(order)
                val editor = sharedPreferences.edit()
                editor.putString("orders", gson.toJson(orders))
                editor.apply()
                findNavController().navigate(CartFragmentDirections.cartToHome(0))
            }
            val adapterCarts = CartAdapter(requireContext(), carts, object : CartAdapter.ItemClickListener {
                override fun minusClick(position: Int) {
                    val qty = carts[position].qty
                    if(qty == 1) carts.removeAt(position)
                    else{
                        carts[position].qty = qty-1
                    }
                    cartViewModel.setCarts(carts)
                    val updatedCartJson = gson.toJson(carts)
                    val editor = sharedPreferences.edit()
                    editor.putString("cart", updatedCartJson)
                    editor.apply()
                }
                override fun plusClick(position: Int) {
                    val qty = carts[position].qty
                    carts[position].qty = qty+1
                    cartViewModel.setCarts(carts)
                    val updatedCartJson = gson.toJson(carts)
                    val editor = sharedPreferences.edit()
                    editor.putString("cart", updatedCartJson)
                    editor.apply()
                }
            })
            val cartView = root.findViewById<RecyclerView>(R.id.cartList)
            cartView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            cartView.adapter = adapterCarts
            cartViewModel.setCarts(carts)
        }

        cartViewModel.carts.observe(viewLifecycleOwner) { carts ->
            var subtotal = 0;
            for(item in carts){
                subtotal += item.menu.price*item.qty
            }
            val tax = subtotal*10/100
            val total = subtotal + tax;

            root.findViewById<TextView>(R.id.subtotal).text = "IDR "+formatNumber(subtotal)
            root.findViewById<TextView>(R.id.subtotal).text = "IDR "+formatNumber(subtotal)
            root.findViewById<TextView>(R.id.total).text = "IDR "+formatNumber(total)
            root.findViewById<TextView>(R.id.tax).text = "IDR "+formatNumber(tax)
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