package com.example.firebasekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebasekotlin.DashboardActivity
import com.example.firebasekotlin.Models.CustomerModel
import com.example.firebasekotlin.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth


class CustomerInsertionActivity : AppCompatActivity() {
    private lateinit var etCustomerName : EditText
    private lateinit var etCustomerPhone : EditText
    private lateinit var etCustomerEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnSaveCustomerData : Button
    private lateinit var fireBaseAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_insertion)

        etCustomerName = findViewById(R.id.etCustomerName)
        etCustomerPhone = findViewById(R.id.etCustomerPhone)
        etCustomerEmail = findViewById(R.id.etCustomerEmail)
        etPassword  = findViewById(R.id.etPassword)
        btnSaveCustomerData = findViewById(R.id.btnSaveCustomer)
        dbRef = FirebaseDatabase.getInstance().getReference("Customers")

        btnSaveCustomerData.setOnClickListener {

            saveCustomerData()
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)


        }
    }
    private fun saveCustomerData(){

        //getting values
        val CustomerName = etCustomerName.text.toString()
        val CustomerPhone = etCustomerPhone.text.toString()
        val CustomerEmail = etCustomerEmail.text.toString()
        val Password = etPassword.text.toString()

        if (CustomerName .isEmpty()){
            etCustomerName .error ="Please enter Customer Name"
        }

        if (CustomerPhone.isEmpty()){
            etCustomerPhone.error ="Please enter Customer Phone"
        }

        if (CustomerEmail.isEmpty()){
            etCustomerEmail.error ="Please enter Customer Email"
        }

        if (Password.isEmpty()){
            etPassword.error ="Please enter Password"
        }

        val CustomerId = dbRef.push().key!!

        val Customer = CustomerModel(CustomerId,CustomerName,CustomerPhone,CustomerEmail)

        dbRef.child(CustomerId).setValue(Customer)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etCustomerName.text.clear()
                etCustomerPhone.text.clear()
                etCustomerEmail.text.clear()
                etPassword.text.clear()




                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }
            }
    }
