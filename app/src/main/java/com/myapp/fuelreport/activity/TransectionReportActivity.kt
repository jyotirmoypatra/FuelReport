package com.myapp.fuelreport.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myapp.fuelreport.R
import com.myapp.fuelreport.adapter.FuelTypeAdapter
import com.myapp.fuelreport.model.TransactionModel
import com.myapp.fuelreport.adapter.TransactionAdapter
import com.myapp.fuelreport.adapter.TransactionTypeAdapter
import com.myapp.fuelreport.model.Fuel
import com.myapp.fuelreport.model.TransactionType

class TransectionReportActivity : AppCompatActivity() {
    private lateinit var menuBtnTransaction: ImageView
    private lateinit var Transaction_drawer_layout: DrawerLayout
    private lateinit var dateTransaction: TextView
    private lateinit var recyclerTransaction: RecyclerView
    private lateinit var recyclerViewTransactionType: RecyclerView
    private lateinit var addBtn: LinearLayout
    private lateinit var transactionTypeHeading: TextView
    private lateinit var main_content_transaction: LinearLayout

    private var transactionList = mutableListOf<TransactionModel>()
    private var transactionTypeList: List<TransactionType> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_transection_report)

        main_content_transaction = findViewById(R.id.main_content_transaction)
        menuBtnTransaction = findViewById(R.id.menuBtnTransaction)
        Transaction_drawer_layout = findViewById(R.id.Transaction_drawer_layout)
        dateTransaction = findViewById(R.id.dateTransaction)
        recyclerTransaction = findViewById(R.id.recyclerTransaction)
        recyclerViewTransactionType = findViewById(R.id.recyclerViewTransactionType)
        addBtn = findViewById(R.id.addBtn)
        transactionTypeHeading = findViewById(R.id.transactionTypeHeading)


        val selectedDate = intent.getStringExtra("selectedDate")
        dateTransaction.text = selectedDate ?: "No Date Selected"

        val transactionTypeListJson = intent.getStringExtra("transactionTypeListJson")
        if (!transactionTypeListJson.isNullOrEmpty()) {
            // Use Gson to parse the JSON string
            val gson = Gson()
            val listType = object : TypeToken<List<TransactionType>>() {}.type
             transactionTypeList = gson.fromJson(transactionTypeListJson, listType)

        }

        menuBtnTransaction.setOnClickListener {
            if (Transaction_drawer_layout.isDrawerOpen(GravityCompat.START)) {
                Transaction_drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                Transaction_drawer_layout.openDrawer(GravityCompat.START)
            }
        }


        // Set up RecyclerView
        transactionList.add(TransactionModel("New Item", "Description"))
        transactionList.add(TransactionModel("New Item", "Description"))
        transactionList.add(TransactionModel("New Item", "Description"))
        val adapter = TransactionAdapter(this,transactionList)
        recyclerTransaction.layoutManager = LinearLayoutManager(this)
        recyclerTransaction.adapter = adapter


        // Set up RecyclerView Fuel
        val transactionTypeAdapter = TransactionTypeAdapter(this, transactionTypeList,this)
        recyclerViewTransactionType.layoutManager = LinearLayoutManager(this)
        recyclerViewTransactionType.adapter = transactionTypeAdapter


        addBtn.setOnClickListener {
            // Add a new item to the transaction list
            transactionList.add(TransactionModel("New Item", "Description"))

            // Notify the adapter about the new item
            adapter.notifyItemInserted(transactionList.size - 1)
            recyclerTransaction.scrollToPosition(transactionList.size - 1)
        }



    }

    fun onFuelItemClick(fuelId: Int,name:String) {
        Log.d("click-item", "" + fuelId)


        transactionTypeHeading.text = name

        Transaction_drawer_layout.closeDrawer(GravityCompat.START)

    }
}