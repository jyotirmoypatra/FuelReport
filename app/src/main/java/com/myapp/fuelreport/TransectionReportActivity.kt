package com.myapp.fuelreport

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TransectionReportActivity : AppCompatActivity() {
    private lateinit var menuBtnTransaction: ImageView
    private lateinit var Transaction_drawer_layout: DrawerLayout
    private lateinit var dateTransaction: TextView
    private lateinit var recyclerTransaction: RecyclerView
    private lateinit var addBtn: LinearLayout

    private var transactionList = mutableListOf<TransactionModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_transection_report)


        menuBtnTransaction = findViewById(R.id.menuBtnTransaction)
        Transaction_drawer_layout = findViewById(R.id.Transaction_drawer_layout)
        dateTransaction = findViewById(R.id.dateTransaction)
        recyclerTransaction = findViewById(R.id.recyclerTransaction)
        addBtn = findViewById(R.id.addBtn)


        val selectedDate = intent.getStringExtra("selectedDate")
        dateTransaction.text = selectedDate ?: "No Date Selected"

        menuBtnTransaction.setOnClickListener {
            if (Transaction_drawer_layout.isDrawerOpen(GravityCompat.START)) {
                Transaction_drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                Transaction_drawer_layout.openDrawer(GravityCompat.START)
            }
        }


        // Set up RecyclerView
        val adapter = TransactionAdapter(this,transactionList)
        recyclerTransaction.layoutManager = LinearLayoutManager(this)
        recyclerTransaction.adapter = adapter

//        addBtn.setOnClickListener {
//            transactionList.add(TransactionModel("", ""))
//            adapter.updateData(transactionList)
//        }
        addBtn.setOnClickListener {
            // Add a new item to the transaction list
            transactionList.add(TransactionModel("New Item", "Description"))

            // Notify the adapter about the new item
            adapter.notifyItemInserted(transactionList.size - 1)
            recyclerTransaction.scrollToPosition(transactionList.size - 1)
        }


    }
}