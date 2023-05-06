package com.example.firebasekotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.Models.DrugModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class InsertionActivity : AppCompatActivity(){

    private lateinit var etDrugName : EditText
    private lateinit var etDrugBrand : EditText
    private lateinit var etDrugPrice : EditText
    private lateinit var etQuantity : EditText
    private lateinit var btnSaveData : Button

    private lateinit var dbRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etDrugName = findViewById(R.id.etDrugName)
        etDrugBrand = findViewById(R.id.etDrugBrand)
        etDrugPrice = findViewById(R.id.etDrugPrice)
        etQuantity = findViewById(R.id.etQuantity)
        btnSaveData = findViewById(R.id.btnSave)
        dbRef =FirebaseDatabase.getInstance().getReference("Drugs")

        btnSaveData.setOnClickListener {

         saveDrugData()
        }
    }
    private fun saveDrugData(){

        //getting values
        val DrugName = etDrugName.text.toString()
        val DrugBrand = etDrugBrand.text.toString()
        val DrugPrice = etDrugPrice.text.toString()
        val Quantity = etQuantity.text.toString()

        if (DrugName.isEmpty()){
            etDrugName.error ="Please enter Drug Name"
        }

        if (DrugBrand.isEmpty()){
            etDrugBrand.error ="Please enter Drug Brand"
        }

        if (DrugPrice.isEmpty()){
            etDrugPrice.error ="Please enter Drug Price"
        }

        if (Quantity.isEmpty()){
            etQuantity.error ="Please enter Quantity"
        }

        val DrugId = dbRef.push().key!!

        val Drug = DrugModel(DrugId,DrugName,DrugBrand,DrugPrice,Quantity)

        dbRef.child(DrugId).setValue(Drug)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_LONG).show()

                etDrugName.text.clear()
                etDrugBrand.text.clear()
                etDrugPrice.text.clear()
                etQuantity.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }
    }
}