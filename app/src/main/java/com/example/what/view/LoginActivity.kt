package com.example.what.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.what.view.MainActivity
import com.example.what.R
import com.example.what.model.User
import com.example.what.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        if (viewModel.isLoggedIn(this)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.login_layout)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val usernameText = findViewById<EditText>(R.id.username)
        val passwordText = findViewById<EditText>(R.id.password)
        loginButton.setOnClickListener {
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()
            val user = User(username, password)

            if (viewModel.login(this, user)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional: finish the LoginActivity to prevent going back to it with the back button
            } else {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}