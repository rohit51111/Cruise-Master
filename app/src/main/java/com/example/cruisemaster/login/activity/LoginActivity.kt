package com.example.cruisemaster.login.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cruisemaster.MainActivity
import com.example.cruisemaster.R
import com.example.cruisemaster.login.viewmodel.LoginViewModel
import retrofit2.HttpException
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        // Clear previous login state if needed
        val intent = intent
        if (intent.flags and Intent.FLAG_ACTIVITY_CLEAR_TASK != 0) {
            val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
            val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
            editTextUsername.setText("")
            editTextPassword.setText("")
        }

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)

        btnLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (validateInputs(username, password)) {
                loginViewModel.login(username, password)
            }
        }

        loginViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess {
                if (it == "success") {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Ensure LoginActivity is finished
                    showToast("Login successful!", Color.GREEN)
                } else {
                    showToast("Unexpected response: $it", Color.RED)
                }
            }.onFailure { e ->
                val errorCode = (e as? HttpException)?.code()
                when (errorCode) {
                    404 -> showToast("Email or password is wrong", Color.RED)
                    403 -> showToast("You don't have permission to access", Color.RED)
                    else -> showToast("An error occurred", Color.RED)
                }
            }
        })
    }

    private fun validateInputs(username: String, password: String): Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            showToast("Fields are empty", Color.RED)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            showToast("UserId should be a valid email", Color.RED)
            return false
        }
        val passwordPattern = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
        )
        if (!passwordPattern.matcher(password).matches()) {
            showToast("Password should contain special symbols, capital and small letters, and numbers", Color.RED)
            return false
        }
        return true
    }

    private fun showToast(message: String, backgroundColor: Int) {
        val toastLayout = layoutInflater.inflate(R.layout.custom_toast, null)
        val toastText = toastLayout.findViewById<TextView>(R.id.toast_text)
        toastText.text = message
        toastLayout.setBackgroundColor(backgroundColor)

        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastLayout
        toast.show()
    }
}