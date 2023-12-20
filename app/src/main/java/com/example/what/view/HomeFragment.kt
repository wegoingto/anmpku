package com.example.what.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.what.R
import com.example.what.databinding.FragmentHomeBinding
import com.example.what.model.Menu
import com.example.what.viewmodel.HomeViewModel


class HomeFragment() : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = HomeViewModel()
        val sharedPreferences = requireContext().getSharedPreferences("WHAT", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val role = sharedPreferences.getString("role", "Waitress")
        val tbl = arguments?.getInt("table")?.toInt() ?: 0

        var currentTable = tbl
        homeViewModel.setTable(currentTable.toString())
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if(tbl !== 0){
            root.findViewById<LinearLayout>(R.id.menu).visibility = View.VISIBLE;
        }
        homeViewModel.username.observe(viewLifecycleOwner) {
            binding.cardLayout.cardUsername.text = it
        }
        homeViewModel.role.observe(viewLifecycleOwner) {
            binding.cardLayout.cardRole.text = it
        }

        homeViewModel.table.observe(viewLifecycleOwner) {
            val serveLayout = binding.serveLayout
            if (currentTable != 0) {
                var newLayout = LayoutInflater.from(context).inflate(R.layout.layout_serve, serveLayout, false)
                serveLayout.removeAllViews()
                serveLayout.addView(newLayout)
                val serveButton = binding.serveLayout.findViewById<TextView>(R.id.change_table)
                if (serveButton != null) {

                    val btnCart = binding.serveLayout.findViewById<Button>(R.id.btnCart)
                    btnCart.setOnClickListener {
                        findNavController().navigate(HomeFragmentDirections.homeToCart(currentTable))
                    }
                    serveButton.setOnClickListener {
                        currentTable = 0
                        homeViewModel.setTable(currentTable.toString())
                        root.findViewById<LinearLayout>(R.id.menu).visibility = View.GONE;
                    }
                }
                binding.serveLayout.findViewById<TextView>(R.id.number_table)?.text = "Table Number "+it.toString()

            }else{
                    val newLayout = LayoutInflater.from(context).inflate(R.layout.layout_new_serve, serveLayout, false)
                    serveLayout.removeAllViews()
                    serveLayout.addView(newLayout)
                    val serveButton = binding.serveLayout.findViewById<Button>(R.id.serve_button)
                    if (serveButton != null) {
                        serveButton.setOnClickListener {
                            val editor = sharedPreferences.edit()
                            editor.putString("cart", "[]")
                            editor.apply()
                            var table = binding.serveLayout.findViewById<EditText>(R.id.table_number).text;
                            currentTable = table.toString().toInt()
                            homeViewModel.setTable(currentTable.toString())
                            root.findViewById<LinearLayout>(R.id.menu).visibility = View.VISIBLE;
                    }
                }
            }
        }
        val appetizerView = root.findViewById<RecyclerView>(R.id.appetizerView)
        val maincourseView = root.findViewById<RecyclerView>(R.id.maincourseView)
        val drinksView = root.findViewById<RecyclerView>(R.id.drinksView)
        appetizerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        maincourseView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        drinksView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val appetizerList = listOf(
            Menu("Lumpia Ayam1", "https://img.kurio.network/OOo7gzHmQoP2Y_P8COlidb77Xbs=/1200x1200/filters:quality(80)/https://kurio-img.kurioapps.com/21/06/28/79fc380f-6664-4de3-92c0-34884fc7af56.jpe",10000,1,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            Menu("Lumpia Ayam2", "https://img.kurio.network/OOo7gzHmQoP2Y_P8COlidb77Xbs=/1200x1200/filters:quality(80)/https://kurio-img.kurioapps.com/21/06/28/79fc380f-6664-4de3-92c0-34884fc7af56.jpe",11000,1,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            Menu("Lumpia Ayam3", "https://img.kurio.network/OOo7gzHmQoP2Y_P8COlidb77Xbs=/1200x1200/filters:quality(80)/https://kurio-img.kurioapps.com/21/06/28/79fc380f-6664-4de3-92c0-34884fc7af56.jpe",12000,1,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
        )
        val maincourseList = listOf(
            Menu("MC 1", "https://img.kurio.network/OOo7gzHmQoP2Y_P8COlidb77Xbs=/1200x1200/filters:quality(80)/https://kurio-img.kurioapps.com/21/06/28/79fc380f-6664-4de3-92c0-34884fc7af56.jpe",12000,2,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
        )
        val drinksList = listOf(
            Menu("DRINKS 1", "https://img.kurio.network/OOo7gzHmQoP2Y_P8COlidb77Xbs=/1200x1200/filters:quality(80)/https://kurio-img.kurioapps.com/21/06/28/79fc380f-6664-4de3-92c0-34884fc7af56.jpe",12000,3,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
        )
        val adapterApetizer = MenuAdapter(requireContext(),emptyList(), {
                selectedItem -> findNavController().navigate(HomeFragmentDirections.viewMenu(selectedItem, currentTable))

        })
        val adapterMaincourse = MenuAdapter(requireContext(),emptyList(), {
                selectedItem -> findNavController().navigate(HomeFragmentDirections.viewMenu(selectedItem, currentTable))

        })
        val adapterDrinks = MenuAdapter(requireContext(),emptyList(), {
                selectedItem -> findNavController().navigate(HomeFragmentDirections.viewMenu(selectedItem, currentTable))
        })
        appetizerView.adapter = adapterApetizer
        maincourseView.adapter = adapterMaincourse
        drinksView.adapter = adapterDrinks
        homeViewModel.appetizerList.observe(viewLifecycleOwner) { items ->
            adapterApetizer.updateData(items)
        }
        homeViewModel.maincourseList.observe(viewLifecycleOwner) { items ->
            adapterMaincourse.updateData(items)

        }
        homeViewModel.drinksList.observe(viewLifecycleOwner) { items ->
            adapterDrinks.updateData(items)

        }
        homeViewModel.setMenu(appetizerList)
        homeViewModel.setMenu(maincourseList)
        homeViewModel.setMenu(drinksList)
        homeViewModel.setUsername(username)
        homeViewModel.setRole(role)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun newInstance(table: Int): HomeFragment? {
        val fragment = HomeFragment()
        val args = Bundle()
        args.putInt("table", table)
        fragment.arguments = args
        return fragment
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Disable the back button
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                // Disable the back button functionality
                return@setOnKeyListener true
            }
            false
        }
    }
}