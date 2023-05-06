package com.example.firebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.firebasekotlin.Activity.CustomerFetchingActivity


private lateinit var imageButton: ImageButton
private lateinit var additem: ImageButton
private lateinit var imageButton3: ImageButton
private lateinit var imageButton2: ImageButton
private lateinit var btndelivery: ImageButton
private lateinit var imageButton6: ImageButton

class PharmacyDashboardActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_dashboard)
        imageButton = findViewById<ImageButton>(R.id.imageButton)
        additem = findViewById<ImageButton>(R.id.additem)
        imageButton3 = findViewById<ImageButton>(R.id.imageButton3)
        imageButton2 = findViewById<ImageButton>(R.id.imageButton2)
        imageButton6 = findViewById<ImageButton>(R.id.imageButton6)

        imageButton.setOnClickListener {
            val intent = Intent(this, PharmacyDashboardActivity::class.java)
            startActivity(intent)
        }

        additem.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        imageButton3.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

        imageButton2.setOnClickListener {
            val intent = Intent(this, CustomerFetchingActivity::class.java)
            startActivity(intent)
        }

        imageButton6.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

    }
}