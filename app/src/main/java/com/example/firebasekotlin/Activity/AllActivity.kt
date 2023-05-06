package com.example.firebasekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firebasekotlin.*
import com.google.android.material.navigation.NavigationView

class AllActivity : AppCompatActivity() {
    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button
    private lateinit var btnFetchingCustomer: Button
    private lateinit var btnInsertionCustomer: Button
    private lateinit var btnFetchingDelivery: Button
    private lateinit var btnInsertionDelivery: Button
    private lateinit var btnFetchingPayment: Button
    private lateinit var btnInsertionPayment: Button
    lateinit var toggle: ActionBarDrawerToggle





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all)

        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData =findViewById(R.id.btnFetchData)
        btnFetchingCustomer = findViewById(R.id.btnFetchCustomer)
        btnInsertionCustomer = findViewById(R.id.btnInsertCustomer)
        btnFetchingDelivery = findViewById(R.id.btnFetchDelivery)
        btnInsertionDelivery = findViewById(R.id.btnInsertDelivery)
        btnFetchingPayment = findViewById(R.id.btnFetchPayment)
        btnInsertionPayment = findViewById(R.id.btnInsertPayment)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){


                R.id.nav_home -> Toast.makeText(applicationContext,"Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.nav_message -> Toast.makeText(applicationContext,"Clicked Message", Toast.LENGTH_SHORT).show()
                R.id.nav_sync -> Toast.makeText(applicationContext,"Clicked Sync", Toast.LENGTH_SHORT).show()
                R.id.nav_trash -> Toast.makeText(applicationContext,"Clicked Delete", Toast.LENGTH_SHORT).show()
                R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Setting", Toast.LENGTH_SHORT).show()
                R.id.nav_login -> Toast.makeText(applicationContext,"Clicked Login", Toast.LENGTH_SHORT).show()
                R.id.nav_share -> Toast.makeText(applicationContext,"Clicked Share", Toast.LENGTH_SHORT).show()
                R.id.nav_rate -> Toast.makeText(applicationContext,"Clicked rate us", Toast.LENGTH_SHORT).show()
            }
            true
        }



        btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

        btnFetchingCustomer.setOnClickListener {
            val intent = Intent(this, CustomerFetchingActivity::class.java)
            startActivity(intent)
        }

        btnInsertionCustomer.setOnClickListener {
            val intent = Intent(this, CustomerInsertionActivity::class.java)
            startActivity(intent)
        }


        btnFetchingDelivery.setOnClickListener {
            val intent = Intent(this, DeliveryFetchingActivity::class.java)
            startActivity(intent)
        }

        btnInsertionDelivery.setOnClickListener {
            val intent = Intent(this, DeliveryInsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchingPayment.setOnClickListener {
            val intent = Intent(this, PaymentFetchingActivity::class.java)
            startActivity(intent)
        }

        btnInsertionPayment.setOnClickListener {
            val intent = Intent(this, PaymentInsertionActivity::class.java)
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