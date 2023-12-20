package com.example.what.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.what.model.Menu

class MenuViewModel : ViewModel() {
    private val _menu = MutableLiveData<Menu>()
    val menu: MutableLiveData<Menu> get() = _menu
    fun setMenu(item: Menu){
        _menu.value = item
    }
}