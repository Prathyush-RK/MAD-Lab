package com.example.fragments

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btBasic : Button = findViewById(R.id.btBasic)
        val btMarks : Button = findViewById(R.id.btMarks)

        btBasic.setOnClickListener {
            val fragment = StudentBasicFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }

        btMarks.setOnClickListener {
            val fragment = StudentMarksFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }
    }
}