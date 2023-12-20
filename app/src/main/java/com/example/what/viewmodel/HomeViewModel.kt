package com.example.what.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.what.R
import com.example.what.model.Menu


class HomeViewModel : ViewModel() {
    private lateinit var user: UserViewModel
    private val _username = MutableLiveData<String>()
    private val _table = MutableLiveData<String>()
    private val _role = MutableLiveData<String>()
    private val _selectedLayoutId = MutableLiveData<Int>(R.layout.layout_new_serve)
    private val _appetizerList = MutableLiveData<List<Menu>>()
    private val _maincourseList = MutableLiveData<List<Menu>>()
    private val _drinksList = MutableLiveData<List<Menu>>()

    // Expose the LiveData object to the Fragment
    val appetizerList: MutableLiveData<List<Menu>> get() = _appetizerList
    val maincourseList: MutableLiveData<List<Menu>> get() = _maincourseList
    val drinksList: MutableLiveData<List<Menu>> get() = _drinksList
    val table: LiveData<String>
        get() = _table
    val username: LiveData<String>
        get() = _username
    val role: LiveData<String>
        get() = _role
    val selectedLayoutId: LiveData<Int>
        get() = _selectedLayoutId

    fun setMenu(item: List<Menu>){
        val category = item[0].cat
        if(category == 1) _appetizerList.value = item
        else if (category == 2) _maincourseList.value = item
        else _drinksList.value = item
    }
    fun setTable(table: String?){
        _table.value = if(table != null){table}else{"error"}
    }
    fun setUsername(username: String?){
        _username.value = username!!
    }
    fun setRole(role: String?){
        _role.value = role!!
    }
}