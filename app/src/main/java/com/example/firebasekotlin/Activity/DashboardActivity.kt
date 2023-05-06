package com.example.firebasekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebasekotlin.Activity.CustomerFetchingActivity
import com.google.android.material.navigation.NavigationView


private lateinit var btnhome: ImageButton
private lateinit var btn_cart: ImageButton
private lateinit var btnsearch: ImageButton
private lateinit var btnpayment: ImageButton
private lateinit var btndelivery: ImageButton
private lateinit var btnprofile: ImageButton
lateinit var toggle: ActionBarDrawerToggle

class DashboardActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        btnhome = findViewById<ImageButton>(R.id.btnhome)
        btn_cart = findViewById<ImageButton>(R.id.btn_cart)
        btnsearch = findViewById<ImageButton>(R.id.btnsearch)
        btnpayment = findViewById<ImageButton>(R.id.btnpayment)
        btndelivery = findViewById<ImageButton>(R.id.btndelivery)
        btnprofile = findViewById<ImageButton>(R.id.btnprofile)


        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view )

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){


                R.id.nav_home -> Toast.makeText(applicationContext,"Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.nav_message -> Toast.makeText(applicationContext,"Clicked Message", Toast.LENGTH_SHORT).show()
                R.id.nav_sync -> Toast.makeText(applicationContext,"Clicked Sync", Toast.LENGTH_SHORT).show()
                R.id.nav_trash -> Toast.makeText(applicationContext,"Clicked Delete", Toast.LENGTH_SHORT).show()
                R.id.nav_setting-> Toast.makeText(applicationContext,"Clicked Setting", Toast.LENGTH_SHORT).show()
                R.id.nav_login -> Toast.makeText(applicationContext,"Clicked Login", Toast.LENGTH_SHORT).show()
                R.id.nav_share -> Toast.makeText(applicationContext,"Clicked Share", Toast.LENGTH_SHORT).show()
                R.id.nav_rate-> Toast.makeText(applicationContext,"Clicked rate us", Toast.LENGTH_SHORT).show()
            }
            true
        }

        btnhome.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        btn_cart.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnsearch.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

        btnpayment.setOnClickListener {
            val intent = Intent(this, PaymentInsertionActivity::class.java)
            startActivity(intent)
        }

        btndelivery.setOnClickListener {
            val intent = Intent(this, DeliveryInsertionActivity::class.java)
            startActivity(intent)
        }
        btnprofile.setOnClickListener {
            val intent = Intent(this, CustomerFetchingActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}