package com.example.firebasekotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.Adapter.DrugAdapter
import com.example.firebasekotlin.Models.DrugModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {
    private lateinit var drugRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var drugList: ArrayList<DrugModel>
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)
        drugRecyclerView = findViewById(R.id.tvDrug)
        drugRecyclerView.layoutManager = LinearLayoutManager(this)
        drugRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        drugList = arrayListOf<DrugModel>()
        dbRef =FirebaseDatabase.getInstance().getReference("Drugs")
        getDrugsData()

    }
    private fun getDrugsData(){
        drugRecyclerView.visibility = View.GONE
        tvLoadingData.visibility=View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Drugs")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                drugList.clear()
                if (snapshot.exists()){
                    for (DrugSnap in snapshot.children){
                        val DrugData = DrugSnap.getValue(DrugModel::class.java)
                        drugList.add(DrugData!!)
                    }
                    val mAdapter = DrugAdapter(drugList)
                    drugRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : DrugAdapter.onItemClickListener{
                        override  fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, DrugDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("DrugId", drugList[position].DrugId)
                            intent.putExtra("DrugName", drugList[position].DrugName)
                            intent.putExtra("DrugBrand", drugList[position].DrugBrand)
                            intent.putExtra("DrugPrice", drugList[position].DrugPrice)
                            intent.putExtra("Quantity", drugList[position].Quantity)
                            startActivity(intent)
                        }

                    })

                    drugRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}