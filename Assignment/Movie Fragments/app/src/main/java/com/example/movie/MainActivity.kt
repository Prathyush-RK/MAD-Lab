package com.example.movie

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btBasic : Button = findViewById(R.id.btBasic)
        val btAdd : Button  = findViewById(R.id.btAdd)

        btBasic.setOnClickListener {
            val fragment = MovieBasicFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }

        btAdd.setOnClickListener {
            val fragment = MovieAddFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()

        }
    }
}