package com.example.firebasekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.Adapter.DeliveryAdapter
import com.example.firebasekotlin.Models.DeliveryModel
import com.example.firebasekotlin.R
import com.google.firebase.database.*

class DeliveryFetchingActivity : AppCompatActivity() {
    private lateinit var deliveryRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var deliveryList: ArrayList<DeliveryModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_fetching)
        deliveryRecyclerView = findViewById(R.id.tvDelivery)
        deliveryRecyclerView.layoutManager = LinearLayoutManager(this)
        deliveryRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        deliveryList = arrayListOf<DeliveryModel>()
        dbRef = FirebaseDatabase.getInstance().getReference("Deliveries")
        getDeliveriesData()

    }
    private fun getDeliveriesData(){
        deliveryRecyclerView.visibility = View.GONE
        tvLoadingData.visibility= View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Deliveries")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deliveryList.clear()
                if (snapshot.exists()){
                    for (DeliverySnap in snapshot.children){
                        val DeliveriesData = DeliverySnap.getValue(DeliveryModel::class.java)
                        deliveryList.add(DeliveriesData!!)
                    }
                    val mAdapter = DeliveryAdapter(deliveryList)
                    deliveryRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : DeliveryAdapter.onItemClickListener{
                        override  fun onItemClick(position: Int) {

                            val intent = Intent(this@DeliveryFetchingActivity, DeliveryDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("DeliveryId", deliveryList[position].DeliveryId)
                            intent.putExtra("DeliveryAddress", deliveryList[position].DeliveryAddress)
                            intent.putExtra("CustomerContactNumber", deliveryList[position].CustomerContactNumber)
                            intent.putExtra("DeliveryDate", deliveryList[position].DeliveryDate)
                            intent.putExtra("DeliveryMan", deliveryList[position].DeliveryMan)
                            startActivity(intent)
                        }

                    })

                    deliveryRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}