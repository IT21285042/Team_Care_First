package com.example.firebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebasekotlin.Models.DeliveryModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeliveryInsertionActivity : AppCompatActivity() {
    private lateinit var etDeliveryAddress : EditText
    private lateinit var etCustomerContactNumber: EditText
    private lateinit var etDeliveryDate : EditText
    private lateinit var etDeliveryMan : EditText
    private lateinit var btnSaveDeliveryData : Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_insertion)

        etDeliveryAddress = findViewById(R.id.etDeliveryAddress)
        etCustomerContactNumber = findViewById(R.id.etCustomerContactNumber)
        etDeliveryDate = findViewById(R.id.etDeliveryDate)
        etDeliveryMan  = findViewById(R.id.etDeliveryMan )
        btnSaveDeliveryData = findViewById(R.id.btnSaveDelivery)
        dbRef = FirebaseDatabase.getInstance().getReference("Deliveries")

        btnSaveDeliveryData.setOnClickListener {

            saveDeliveryData()
        }
    }
    private fun saveDeliveryData(){

        //getting values
        val DeliveryAddress = etDeliveryAddress.text.toString()
        val CustomerContactNumber = etCustomerContactNumber.text.toString()
        val DeliveryDate = etDeliveryDate.text.toString()
        val DeliveryMan  = etDeliveryMan .text.toString()

        if (DeliveryAddress .isEmpty()){
            etDeliveryAddress .error ="Please enter DeliveryAddress"
        }

        if (CustomerContactNumber.isEmpty()){
            etCustomerContactNumber.error ="Please enter CustomerContactNumber"
        }

        if (DeliveryDate.isEmpty()){
            etDeliveryDate.error ="Please enter DeliveryDate"
        }

        if (DeliveryMan.isEmpty()){
            etDeliveryMan.error ="Please enter DeliveryMan"
        }

        val DeliveryId = dbRef.push().key!!

        val Delivery = DeliveryModel(DeliveryId,DeliveryAddress,CustomerContactNumber,DeliveryDate,DeliveryMan)

        dbRef.child(DeliveryId).setValue(Delivery)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etDeliveryAddress.text.clear()
                etCustomerContactNumber.text.clear()
                etDeliveryDate.text.clear()
                etDeliveryMan.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}