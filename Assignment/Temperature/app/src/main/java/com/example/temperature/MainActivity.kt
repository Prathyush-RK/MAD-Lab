package com.example.temperature   // keep your package name

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val etTemperature: EditText = findViewById(R.id.etTemperature)
        val btnCtoF: Button = findViewById(R.id.btnCtoF)
        val btnFtoC: Button = findViewById(R.id.btnFtoC)
        val tvResult: TextView = findViewById(R.id.tvResult)

        // Celsius to Fahrenheit
        btnCtoF.setOnClickListener {
            val celsius = etTemperature.text.toString().toDouble()
            val fahrenheit = (celsius * 9/5) + 32
            tvResult.text = "Fahrenheit = $fahrenheit"
        }

        // Fahrenheit to Celsius
        btnFtoC.setOnClickListener {
            val fahrenheit = etTemperature.text.toString().toDouble()
            val celsius = (fahrenheit - 32) * 5/9
            tvResult.text = "Celsius = $celsius"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}