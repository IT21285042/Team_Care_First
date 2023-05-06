package com.example.firebasekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebasekotlin.Models.DeliveryModel
import com.example.firebasekotlin.R
import com.google.firebase.database.FirebaseDatabase

class DeliveryDetailsActivity : AppCompatActivity() {
    private lateinit var tvDeliveryId: TextView
    private lateinit var tvDeliveryAddress: TextView
    private lateinit var tvCustomerContactNumber: TextView
    private lateinit var tvDeliveryDate: TextView
    private lateinit var tvDeliveryMan: TextView
    private lateinit var btnUpdateDelivery: Button
    private lateinit var btnDeleteDelivery: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_details)

        initView()
        setValuesToViews()

        btnUpdateDelivery.setOnClickListener {
            openUpdateDeliveryDialog(
                intent.getStringExtra("DeliveryId").toString(),
                intent.getStringExtra("DeliveryAddress").toString(),


                )
        }

        btnDeleteDelivery.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("DeliveryId").toString()
            )
        }

    }

    private fun initView() {
        tvDeliveryId = findViewById(R.id.tvDeliveryId)
        tvDeliveryAddress = findViewById(R.id.tvDeliveryAddress)
        tvCustomerContactNumber = findViewById(R.id.tvCustomerContactNumber)
        tvDeliveryDate = findViewById(R.id.tvDeliveryDate)
        tvDeliveryMan = findViewById(R.id.tvDeliveryMan)

        btnUpdateDelivery = findViewById(R.id.btnUpdateDelivery)
        btnDeleteDelivery = findViewById(R.id.btnDeleteDelivery)
    }

    private fun setValuesToViews() {
        tvDeliveryId.text = intent.getStringExtra("DeliveryId")
        tvDeliveryAddress.text = intent.getStringExtra("DeliveryAddress")
        tvCustomerContactNumber.text = intent.getStringExtra("CustomerContactNumber")
        tvDeliveryDate.text = intent.getStringExtra("DeliveryDate")
        tvDeliveryMan.text = intent.getStringExtra("DeliveryMan")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Deliveries").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Delivery data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, DeliveryFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDeliveryDialog(
        DeliveryId: String,
        DeliveryAddress: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_delivery_dialog, null)

        mDialog.setView(mDialogView)

        val etDeliveryAddress = mDialogView.findViewById<EditText>(R.id.etDeliveryAddress)
        val etCustomerContactNumber= mDialogView.findViewById<EditText>(R.id.etCustomerContactNumber)
        val etDeliveryDate = mDialogView.findViewById<EditText>(R.id.etDeliveryDate)
        val etDeliveryMan = mDialogView.findViewById<EditText>(R.id.etDeliveryMan)

        val btnUpdateDelivery = mDialogView.findViewById<Button>(R.id.btnUpdateDelivery)

        etDeliveryAddress.setText(intent.getStringExtra("DeliveryAddress").toString())
        etCustomerContactNumber.setText(intent.getStringExtra("CustomerContactNumber").toString())
        etDeliveryDate.setText(intent.getStringExtra("DeliveryDate").toString())
        etDeliveryMan.setText(intent.getStringExtra("DeliveryMan").toString())

        mDialog.setTitle("Updating $DeliveryAddress Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateDelivery.setOnClickListener {
            updateDelivery(
                DeliveryId,
                etDeliveryAddress.text.toString(),
                etCustomerContactNumber.text.toString(),
                etDeliveryDate.text.toString(),
                etDeliveryMan.text.toString()
            )

            Toast.makeText(applicationContext, "Delivery Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvDeliveryAddress.text = etDeliveryAddress.text.toString()
            tvCustomerContactNumber.text = etCustomerContactNumber.text.toString()
            tvDeliveryDate.text = etDeliveryDate.text.toString()
            tvDeliveryMan.text =  etDeliveryMan.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateDelivery(
        id: String,
        address: String,
        number: String,
        date: String,
        deliveryman: String,
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Deliveries").child(id)
        val DeliveriesInfo = DeliveryModel(id, address, number, date,deliveryman)

        dbRef.setValue(DeliveriesInfo)
    }
}