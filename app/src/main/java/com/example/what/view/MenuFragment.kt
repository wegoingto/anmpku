package com.example.what.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.util.Locale
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.what.R
import com.example.what.databinding.FragmentMenuBinding
import com.example.what.model.Cart
import com.example.what.model.Menu
import com.example.what.viewmodel.MenuViewModel
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreferences = requireContext().getSharedPreferences("WHAT", Context.MODE_PRIVATE)
        val cart = sharedPreferences.getString("cart", "[]")
        val gson = Gson()
        val personListType = object : TypeToken<List<Cart>>() {}.type
        val carts = gson.fromJson<MutableList<Cart>>(cart, personListType)

        ViewModelProvider(this).get(MenuViewModel::class.java)
        val menuViewModel = MenuViewModel()
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var currentTable = arguments?.getInt("table")?.toInt() ?: 0
        Log.d("BENGSKY", currentTable.toString())
        val argsMenu = arguments?.getParcelable<Menu>("argsMenu")
        if (argsMenu != null) {
            menuViewModel.setMenu(argsMenu)
        }
        menuViewModel.menu.observe(viewLifecycleOwner) {
            root.findViewById<TextView>(R.id.menuName)?.text = it.name
            root.findViewById<TextView>(R.id.menuDesc)?.text = it.desc
            val formattedNumber = NumberFormat.getNumberInstance(Locale.US).format(it.price)
            root.findViewById<TextView>(R.id.menuPrice)?.text = "IDR "+formattedNumber
            val holder = root.findViewById<ImageView>(R.id.imageView)
            val imageRequest = ImageRequest(
                it.img,
                Response.Listener { response ->
                    holder.setImageBitmap(response)
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
            val minus = root.findViewById<ImageButton>(R.id.minus)
            val plus = root.findViewById<ImageButton>(R.id.plus)
            val qty = root.findViewById<TextView>(R.id.textQty)
            val add = root.findViewById<Button>(R.id.addCart)
            minus.setOnClickListener {
                if (qty.text == "1")
                else{
                    qty.text = (qty.text.toString().toInt() - 1).toString()
                }
            }
            plus.setOnClickListener {
                qty.text = (qty.text.toString().toInt() + 1).toString()
            }
            add.setOnClickListener {
                val qty = qty.text.toString().toInt()
                var found = false
                for (item in carts) {
                    if (item.menu.name == argsMenu?.name) {
                        item.qty = item.qty + qty
                        found = true
                        break
                    }
                }
                if (!found) {
                    val newItem = argsMenu?.let { it1 -> Cart(it1, qty) }
                    if (newItem != null) {
                        carts.add(newItem)
                    }
                }
                val updatedCartJson = gson.toJson(carts)
                val editor = sharedPreferences.edit()
                editor.putString("cart", updatedCartJson)
                editor.apply()
                findNavController().navigate(MenuFragmentDirections.menuToHome(currentTable))
            }
        }

        return root
    }
    fun newInstance(argsMenu: com.example.what.model.Menu?, table: Int): MenuFragment? {
        val fragment = MenuFragment()
        val args = Bundle()
        args.putParcelable("argsMenu", argsMenu)
        args.putInt("table", table)
        fragment.arguments = args
        return fragment
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                return@setOnKeyListener true
            }
            false
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}