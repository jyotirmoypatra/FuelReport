package com.myapp.fuelreport.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.fuelreport.R
import com.myapp.fuelreport.model.TransactionModel
import com.myapp.fuelreport.adapter.TransactionAdapter

class TransectionReportActivity : AppCompatActivity() {
    private lateinit var menuBtnTransaction: ImageView
    private lateinit var Transaction_drawer_layout: DrawerLayout
    private lateinit var dateTransaction: TextView
    private lateinit var recyclerTransaction: RecyclerView
    private lateinit var addBtn: LinearLayout
    private lateinit var CASH: TextView
    private lateinit var UPI: TextView
    private lateinit var CARD: TextView
    private lateinit var CREDIT: TextView
    private lateinit var DISCOUNT: TextView
    private lateinit var transactionTypeHeading: TextView
    private lateinit var main_content_transaction: LinearLayout

    private var transactionList = mutableListOf<TransactionModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_transection_report)

        main_content_transaction = findViewById(R.id.main_content_transaction)
        menuBtnTransaction = findViewById(R.id.menuBtnTransaction)
        Transaction_drawer_layout = findViewById(R.id.Transaction_drawer_layout)
        dateTransaction = findViewById(R.id.dateTransaction)
        recyclerTransaction = findViewById(R.id.recyclerTransaction)
        addBtn = findViewById(R.id.addBtn)
        CASH = findViewById(R.id.CASH)
        UPI = findViewById(R.id.UPI)
        CARD = findViewById(R.id.CARD)
        CREDIT = findViewById(R.id.CREDIT)
        DISCOUNT = findViewById(R.id.DISCOUNT)
        transactionTypeHeading = findViewById(R.id.transactionTypeHeading)


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
        transactionList.add(TransactionModel("New Item", "Description"))
        transactionList.add(TransactionModel("New Item", "Description"))
        transactionList.add(TransactionModel("New Item", "Description"))
        val adapter = TransactionAdapter(this,transactionList)
        recyclerTransaction.layoutManager = LinearLayoutManager(this)
        recyclerTransaction.adapter = adapter


        addBtn.setOnClickListener {
            // Add a new item to the transaction list
            transactionList.add(TransactionModel("New Item", "Description"))

            // Notify the adapter about the new item
            adapter.notifyItemInserted(transactionList.size - 1)
            recyclerTransaction.scrollToPosition(transactionList.size - 1)
        }


        ///side menu action
        CASH.setOnClickListener {
            transactionTypeHeading.text = "CASH"
            Transaction_drawer_layout.closeDrawer(GravityCompat.START)

            CASH.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            CARD.setBackgroundColor(Color.TRANSPARENT)
            UPI.setBackgroundColor(Color.TRANSPARENT)
            CREDIT.setBackgroundColor(Color.TRANSPARENT)
            DISCOUNT.setBackgroundColor(Color.TRANSPARENT)

        }
        CARD.setOnClickListener {
            transactionTypeHeading.text = "CARD"
            Transaction_drawer_layout.closeDrawer(GravityCompat.START)

            CARD.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            CASH.setBackgroundColor(Color.TRANSPARENT)
            UPI.setBackgroundColor(Color.TRANSPARENT)
            CREDIT.setBackgroundColor(Color.TRANSPARENT)
            DISCOUNT.setBackgroundColor(Color.TRANSPARENT)
        }
        UPI.setOnClickListener {
            transactionTypeHeading.text = "UPI"
            Transaction_drawer_layout.closeDrawer(GravityCompat.START)

            UPI.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            CASH.setBackgroundColor(Color.TRANSPARENT)
            CARD.setBackgroundColor(Color.TRANSPARENT)
            CREDIT.setBackgroundColor(Color.TRANSPARENT)
            DISCOUNT.setBackgroundColor(Color.TRANSPARENT)
        }
        CREDIT.setOnClickListener {
            transactionTypeHeading.text = "CREDIT"
            Transaction_drawer_layout.closeDrawer(GravityCompat.START)

            CREDIT.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            CASH.setBackgroundColor(Color.TRANSPARENT)
            CARD.setBackgroundColor(Color.TRANSPARENT)
            UPI.setBackgroundColor(Color.TRANSPARENT)
            DISCOUNT.setBackgroundColor(Color.TRANSPARENT)
        }
        DISCOUNT.setOnClickListener {
            transactionTypeHeading.text = "DISCOUNT"
            Transaction_drawer_layout.closeDrawer(GravityCompat.START)

            DISCOUNT.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            CASH.setBackgroundColor(Color.TRANSPARENT)
            CARD.setBackgroundColor(Color.TRANSPARENT)
            UPI.setBackgroundColor(Color.TRANSPARENT)
            CREDIT.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}