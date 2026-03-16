package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainLayout : LinearLayout = findViewById(R.id.main)
        val rgColor : RadioGroup = findViewById(R.id.rgColor)

        val rbRed : RadioButton = findViewById(R.id.rbRed)
        val rbGreen : RadioButton = findViewById(R.id.rbGreen)
        val rbBlue : RadioButton = findViewById(R.id.rbBlue)
        val btReset : Button = findViewById(R.id.btReset)

        rgColor.setOnCheckedChangeListener { _, checkedId ->

            if (rbRed.isChecked) {
                mainLayout.setBackgroundColor(Color.RED)
            }

            if (rbGreen.isChecked) {
                mainLayout.setBackgroundColor(Color.GREEN)
            }

            if (rbBlue.isChecked) {
                mainLayout.setBackgroundColor(Color.BLUE)
            }
        }

        btReset.setOnClickListener(){
            mainLayout.setBackgroundColor(Color.WHITE)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}