package com.example.digitalcafe

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btValidate: Button = findViewById(R.id.btValidate)

        btValidate.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val collegeEmailPattern = "^[A-Za-z0-9._%+-]+@college\\.edu$".toRegex()

            if (!collegeEmailPattern.matches(email)) {
                Toast.makeText(this, "Enter valid college email id", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val passwordPattern = "^(?=.*[a-zA-Z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{12,}$".toRegex()

            if (!passwordPattern.matches(password)) {
                Toast.makeText(
                    this,
                    "Password must contain uppercase, number, special symbol & min 12 characters",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Validation Successful", Toast.LENGTH_LONG).show()
        }
    }
}