package com.example.check

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvCount: TextView
    private lateinit var btnCheckIn: Button
    private lateinit var btnCheckOut: Button

    private var studentCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect variables to XML elements
        tvCount = findViewById(R.id.tvCount)
        btnCheckIn = findViewById(R.id.btnCheckIn)
        btnCheckOut = findViewById(R.id.btnCheckOut)

        btnCheckIn.setOnClickListener {
            studentCount++
            updateDisplay()
        }

        btnCheckOut.setOnClickListener {
            if (studentCount > 0) {
                studentCount--
                updateDisplay()
            }
        }

        updateDisplay()
    }

    private fun updateDisplay() {
        tvCount.text = studentCount.toString()

        // Disable Check Out button when count is 0
        btnCheckOut.isEnabled = studentCount > 0
    }
}