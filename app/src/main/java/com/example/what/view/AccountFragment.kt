package com.example.what.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.what.databinding.FragmentAccountBinding
import com.example.what.viewmodel.AccountViewModel


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)
        val sharedPreferences = requireContext().getSharedPreferences("WHAT", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        accountViewModel.username.observe(viewLifecycleOwner) {
            binding.cardUsername.text = it
        }
        accountViewModel.setUsername(username)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}