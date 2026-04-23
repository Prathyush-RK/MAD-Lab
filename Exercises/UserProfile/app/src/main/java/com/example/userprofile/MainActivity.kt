package com.example.userprofile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.content.SharedPreferences
import android.widget.*
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private lateinit var share: SharedPreferences

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var etBio: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        share = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etAge = findViewById(R.id.etAge)
        etBio = findViewById(R.id.etBio)
        radioGroup = findViewById(R.id.radioGroupColor)
        btnSave = findViewById(R.id.btnSave)

        loadData()

        btnSave.setOnClickListener {
            saveData()
            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveData() {
        share.edit {

            putString("name", etName.text.toString())
            putString("email", etEmail.text.toString())
            putString("age", etAge.text.toString())
            putString("bio", etBio.text.toString())

            val selectedId = radioGroup.checkedRadioButtonId
            putInt("color", selectedId)

        }
    }

    private fun loadData() {
        etName.setText(share.getString("name", ""))
        etEmail.setText(share.getString("email", ""))
        etAge.setText(share.getString("age", ""))
        etBio.setText(share.getString("bio", ""))

        val savedColorId = share.getInt("color", -1)
        if (savedColorId != -1) {
            radioGroup.check(savedColorId)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}