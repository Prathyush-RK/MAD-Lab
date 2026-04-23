package com.example.guicomponents

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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

        val tv : TextView = findViewById(R.id.tv)
        val btChange : Button =  findViewById(R.id.btChange)
        val btReset : Button = findViewById(R.id.btReset)

        val dfont = tv.typeface
        val dcolor = tv.currentTextColor

        btChange.setOnClickListener {
            tv.setTextColor(Color.RED)
            tv.setTypeface(null, Typeface.BOLD)
        }

        btReset.setOnClickListener {
            tv.setTextColor(dcolor)
            tv.typeface = dfont
        }


        Toast.makeText(this,"Font and Color changed", Toast.LENGTH_LONG).show()

    }
}