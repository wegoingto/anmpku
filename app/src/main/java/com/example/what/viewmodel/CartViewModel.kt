package com.example.what.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.what.model.Cart

class CartViewModel : ViewModel() {

    private val _carts = MutableLiveData<List<Cart>>()
    val carts: LiveData<List<Cart>>
        get() = _carts
    fun setCarts(carts: List<Cart>){
        _carts.value = carts
    }
}