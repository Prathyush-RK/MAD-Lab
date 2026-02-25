package com.example.validation

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
        val etUserName: EditText = findViewById(R.id.etUserName)
        val etPinNumber: EditText = findViewById(R.id.etPinNumber)
        val btLValidate : Button = findViewById(R.id.btValidate)

        btLValidate.setOnClickListener {
            val checkUserName = "[a-zA-Z]+".toRegex()
            val checkPinNumber = "[0-9]{4}".toRegex()

            if(checkUserName.matches(etUserName.text.toString()) && checkPinNumber.matches(etPinNumber.text.toString())){
                Toast.makeText(this, "Success...", Toast.LENGTH_LONG).show()
            }

            else{
                Toast.makeText(this, "Invalid Username / Pin Number", Toast.LENGTH_LONG).show()
            }


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}