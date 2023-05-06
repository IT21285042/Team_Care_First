package com.example.firebasekotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.Models.DrugModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DrugDetailsActivity : AppCompatActivity() {

    private lateinit var tvDrugId: TextView
    private lateinit var tvDrugName: TextView
    private lateinit var tvDrugBrand: TextView
    private lateinit var tvDrugPrice: TextView
    private lateinit var tvQuantity: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("DrugId").toString(),
                intent.getStringExtra("DrugName").toString(),


            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("DrugId").toString()
            )
            showDeleteAlert()
        }

    }

    private fun initView() {
        tvDrugId = findViewById(R.id.tvDrugId)
        tvDrugName = findViewById(R.id.tvDrugName)
        tvDrugBrand = findViewById(R.id.tvDrugBrand)
        tvDrugPrice = findViewById(R.id.tvDrugPrice)
        tvQuantity = findViewById(R.id.tvQuantity)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvDrugId.text = intent.getStringExtra("DrugId")
        tvDrugName.text = intent.getStringExtra("DrugName")
        tvDrugBrand.text = intent.getStringExtra("DrugBrand")
        tvDrugPrice.text = intent.getStringExtra("DrugPrice")
        tvQuantity.text = intent.getStringExtra("Quantity")

    }

    private fun deleteRecord(
        id: String

    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Drugs").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Drug data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        DrugId: String,
        DrugName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etDrugName = mDialogView.findViewById<EditText>(R.id.etDrugName)
        val etDrugBrand = mDialogView.findViewById<EditText>(R.id.etDrugBrand)
        val etDrugPrice = mDialogView.findViewById<EditText>(R.id.etDrugPrice)
        val etQuantity = mDialogView.findViewById<EditText>(R.id.etQuantity)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etDrugName.setText(intent.getStringExtra("DrugName").toString())
        etDrugBrand.setText(intent.getStringExtra("DrugBrand").toString())
        etDrugPrice.setText(intent.getStringExtra("DrugPrice").toString())
        etQuantity.setText(intent.getStringExtra("Quantity").toString())

        mDialog.setTitle("Updating $DrugName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateDrugData(
                DrugId,
                etDrugName.text.toString(),
                etDrugBrand.text.toString(),
                etDrugPrice.text.toString(),
                etQuantity.text.toString()
            )

            Toast.makeText(applicationContext, "Drug Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvDrugName.text = etDrugName.text.toString()
            tvDrugBrand.text = etDrugBrand.text.toString()
            tvDrugPrice.text = etDrugPrice.text.toString()
            tvQuantity.text =  etQuantity.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateDrugData(
        id: String,
        name: String,
        brand: String,
        price: String,
        quantity: String,
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Drugs").child(id)
        val DrugInfo = DrugModel(id, name, price, brand,quantity)
        dbRef.setValue(DrugInfo)
    }


    private fun showDeleteAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete profile?")
            .setTitle("Delete Profile")
            .setPositiveButton("Yes") { dialog, which ->

                val DrugId = FirebaseAuth.getInstance().currentUser?.uid
                val userRef = FirebaseDatabase.getInstance().getReference("Drugs/$DrugId")

// Use the removeValue() method to delete the node from the database
                userRef.removeValue()
                    .addOnSuccessListener {
// Display a success message to the user
                        Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { error ->
// Display an error message to the user
                        Toast.makeText(this, "Error deleting Profile: ${error.message}", Toast.LENGTH_SHORT).show()
                    }

//Delete from Firebase Authentication
                FirebaseAuth.getInstance().currentUser?.delete()
                    ?.addOnSuccessListener {
                        Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                    ?.addOnFailureListener {
                        Toast.makeText(this, "Error deleting Profile", Toast.LENGTH_SHORT)
                    }

//                auth = FirebaseAuth.getInstance()
//                if(auth.currentUser == null){
//                    val intent = Intent(this, PharmacySignUpActivity::class.java)
//                    startActivity(intent)
//                }

            }
            .setNegativeButton("No") { dialog, which ->
// Do nothing

                dialog.cancel()
            }

        val dialog = builder.create()
        dialog.show()
    }
}