package com.example.firebasekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.Adapter.CustomerAdapter
import com.example.firebasekotlin.Models.CustomerModel
import com.example.firebasekotlin.R
import com.google.firebase.database.*

class CustomerFetchingActivity : AppCompatActivity() {
    private lateinit var customerRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var customerList: ArrayList<CustomerModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_fetching)
        customerRecyclerView = findViewById(R.id.tvCustomer)
        customerRecyclerView.layoutManager = LinearLayoutManager(this)
        customerRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        customerList = arrayListOf<CustomerModel>()
        dbRef = FirebaseDatabase.getInstance().getReference("Customers")
        getCustomersData()

    }
    private fun getCustomersData(){
        customerRecyclerView.visibility = View.GONE
        tvLoadingData.visibility= View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Customers")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                customerList.clear()
                if (snapshot.exists()){
                    for (CustomerSnap in snapshot.children){
                        val CustomersData = CustomerSnap.getValue(CustomerModel::class.java)
                        customerList.add(CustomersData!!)
                    }
                    val mAdapter = CustomerAdapter(customerList)
                    customerRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : CustomerAdapter.onItemClickListener{
                        override  fun onItemClick(position: Int) {

                            val intent = Intent(this@CustomerFetchingActivity, CustomerDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("CustomerId", customerList[position].uid)
                            intent.putExtra("CustomerName", customerList[position].cusName)
                            intent.putExtra("CustomerPhone", customerList[position].cusPhone)
                            intent.putExtra("CustomerEmail", customerList[position].cusEmail)
//                            intent.putExtra("Password", customerList[position].)
                            startActivity(intent)
                        }

                    })

                    customerRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}