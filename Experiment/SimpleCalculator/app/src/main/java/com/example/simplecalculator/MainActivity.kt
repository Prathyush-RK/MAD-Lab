package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNumber1 = findViewById<EditText>(R.id.etNumber1)
        val etNumber2 = findViewById<EditText>(R.id.etNumber2)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)

        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnAdd.setOnClickListener {
            val num1 = etNumber1.text.toString().toDouble()
            val num2 = etNumber2.text.toString().toDouble()
            val result = num1 + num2
            tvResult.text = "Result = $result"
        }

        btnSubtract.setOnClickListener {
            val num1 = etNumber1.text.toString().toDouble()
            val num2 = etNumber2.text.toString().toDouble()
            val result = num1 - num2
            tvResult.text = "Result = $result"
        }

        btnMultiply.setOnClickListener {
            val num1 = etNumber1.text.toString().toDouble()
            val num2 = etNumber2.text.toString().toDouble()
            val result = num1 * num2
            tvResult.text = "Result = $result"
        }

        btnDivide.setOnClickListener {
            val num1 = etNumber1.text.toString().toDouble()
            val num2 = etNumber2.text.toString().toDouble()
            val result = num1 / num2
            tvResult.text = "Result = $result"
        }
    }
}