package com.example.firebasekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebasekotlin.Models.CustomerModel
import com.example.firebasekotlin.R
import com.google.firebase.database.FirebaseDatabase

class CustomerDetailsActivity : AppCompatActivity() {
    private lateinit var tvCustomerId: TextView
    private lateinit var tvCustomerName: TextView
    private lateinit var tvCustomerPhone: TextView
    private lateinit var tvCustomerEmail: TextView
    private lateinit var tvPassword: TextView
    private lateinit var btnUpdateCustomer: Button
    private lateinit var btnDeleteCustomer: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)

        initView()
        setValuesToViews()

        btnUpdateCustomer.setOnClickListener {
            openCustomerUpdateDialog(
                intent.getStringExtra("uid").toString(),
                intent.getStringExtra("cusName").toString(),


                )
        }

        btnDeleteCustomer.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("uid").toString()
            )
        }

    }

    private fun initView() {
        tvCustomerId = findViewById(R.id.tvCustomerId)
        tvCustomerName = findViewById(R.id.tvCustomerName)
        tvCustomerPhone = findViewById(R.id.tvCustomerPhone)
        tvCustomerEmail = findViewById(R.id.tvCustomerEmail)
        tvPassword = findViewById(R.id.tvPassword)

        btnUpdateCustomer = findViewById(R.id.btnUpdateCustomer)
        btnDeleteCustomer = findViewById(R.id.btnDeleteCustomer)
    }

    private fun setValuesToViews() {
        tvCustomerId.text = intent.getStringExtra("uid")
        tvCustomerName.text = intent.getStringExtra("cusName")
        tvCustomerPhone.text = intent.getStringExtra("cusPhone")
        tvCustomerEmail.text = intent.getStringExtra("cusEmail")
//        tvPassword.text = intent.getStringExtra("Password")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Customers").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Customer data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, CustomerFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openCustomerUpdateDialog(
        uid: String,
        cusName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_customer_dialog, null)

        mDialog.setView(mDialogView)

        val etCustomerName = mDialogView.findViewById<EditText>(R.id.etCustomerName)
        val etCustomerPhone= mDialogView.findViewById<EditText>(R.id.etCustomerPhone)
        val etCustomerEmail = mDialogView.findViewById<EditText>(R.id.etCustomerEmail)
        val etPassword = mDialogView.findViewById<EditText>(R.id.etPassword)

        val btnUpdateCustomer = mDialogView.findViewById<Button>(R.id.btnUpdateCustomer)

        etCustomerName.setText(intent.getStringExtra("cusName").toString())
        etCustomerPhone.setText(intent.getStringExtra("cusPhone").toString())
        etCustomerEmail.setText(intent.getStringExtra("cusEmail").toString())
//        etPassword.setText(intent.getStringExtra("Password").toString())

        mDialog.setTitle("Updating $cusName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateCustomer.setOnClickListener {
            updateCustomer(
                uid,
                etCustomerName.text.toString(),
                etCustomerPhone.text.toString(),
                etCustomerEmail.text.toString(),
//                etPassword.text.toString()
            )

            Toast.makeText(applicationContext, "Customer Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvCustomerName.text = etCustomerName.text.toString()
            tvCustomerPhone.text = etCustomerPhone.text.toString()
            tvCustomerEmail.text = etCustomerEmail.text.toString()
//            tvPassword.text =  etPassword.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateCustomer(
        id: String,
        name: String,
        phone: String,
        email: String,

    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Customers").child(id)
        val CustomersInfo = CustomerModel(id, name, phone, email)
        dbRef.setValue(CustomersInfo)
    }
}