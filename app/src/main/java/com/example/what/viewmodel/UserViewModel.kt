package com.example.what.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.what.model.User
import com.google.gson.Gson

class UserViewModel : ViewModel() {
    val gson = Gson()
    private val dummyUser = gson.fromJson("{\"username\":\"rei\",\"password\":\"password123\"}", User::class.java);
    fun login(context: Context, user: User): Boolean {
        if(user == dummyUser)
        {
            val sharedPreferences = context.getSharedPreferences("WHAT", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.putString("username", user.username)
            editor.apply()
            return true
        }
        return false
    }
    fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences("WHAT", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.putString("username", null)
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("WHAT", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun getUsername(context: Context): String{
        val sharedPreferences = context.getSharedPreferences("WHAT", Context.MODE_PRIVATE)
        val a= sharedPreferences.getString("username", null)
        return "a";
    }
}
