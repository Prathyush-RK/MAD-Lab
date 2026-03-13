package com.example.checkbox

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
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
        val cbPizza : CheckBox = findViewById(R.id.cbPizza)
        val cbBurger : CheckBox = findViewById(R.id.cbBurger)
        val cbCoffee : CheckBox = findViewById(R.id.cbCoffee)
        val cbMojito : CheckBox = findViewById(R.id.cbMojito)

        val btCalculate : Button = findViewById(R.id.btCalculate)

        btCalculate.setOnClickListener {
            var total = 0
            var strTotal = StringBuilder()
            if(cbPizza.isChecked){
                total += 150
                strTotal.append("Pizza - ₹150  ")
            }
            if(cbBurger.isChecked){
                total += 120
                strTotal.append("Burger - ₹120  ")
            }
            if(cbCoffee.isChecked){
                total += 80
                strTotal.append("Coffee - ₹80  ")
            }
            if(cbMojito.isChecked){
                total += 90
                strTotal.append("Mojito - ₹90  ")
            }

            if (total == 0) {
                Toast.makeText(this, "No item selected", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, strTotal.toString(), Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Total = ₹$total", Toast.LENGTH_LONG).show()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}