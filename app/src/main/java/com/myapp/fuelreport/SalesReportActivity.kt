package com.myapp.fuelreport

import NozzleAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SalesReportActivity : AppCompatActivity() {

    private lateinit var menuBtn: ImageView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var nozzelRecyclerView: RecyclerView
    private lateinit var fuelTypeHeading: TextView
    private lateinit var llHeadingCard: LinearLayout
    private lateinit var Diesel: TextView
    private lateinit var Petrol: TextView
    private lateinit var CNG: TextView
    private lateinit var dateTextView: TextView

    private var nozzleList = mutableListOf<Nozzle>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sales_report)

        menuBtn = findViewById(R.id.menuBtn)
        drawerLayout = findViewById(R.id.drawer_layout)
        nozzelRecyclerView = findViewById(R.id.nozzelRecyclerView)
        fuelTypeHeading = findViewById(R.id.fuelTypeHeading)
        llHeadingCard = findViewById(R.id.llHeadingCard)
        dateTextView = findViewById(R.id.dateTextView)

        Diesel = findViewById(R.id.Diesel)
        Petrol = findViewById(R.id.Petrol)
        CNG = findViewById(R.id.CNG)


        val selectedDate = intent.getStringExtra("selectedDate")
        dateTextView.text = selectedDate ?: "No Date Selected"



        menuBtn.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }


        // Set up RecyclerView
        val adapter = NozzleAdapter(this,nozzleList)
        nozzelRecyclerView.layoutManager = LinearLayoutManager(this)
        nozzelRecyclerView.adapter = adapter




        Diesel.setOnClickListener {
            fuelTypeHeading.text = "DIESEL"

            Diesel.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            Petrol.setBackgroundColor(Color.TRANSPARENT)
            CNG.setBackgroundColor(Color.TRANSPARENT)
            drawerLayout.closeDrawer(GravityCompat.START)

            llHeadingCard.setBackgroundColor(ContextCompat.getColor(this, R.color.lightYellow))

            nozzleList = mutableListOf(
                Nozzle("Nozzle 1", "Active"),
                Nozzle("Nozzle 2", "Active")
            )

            adapter.updateData(nozzleList,"diesel")

        }
        Petrol.setOnClickListener {
            fuelTypeHeading.text = "PETROL"

            Petrol.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            Diesel.setBackgroundColor(Color.TRANSPARENT)
            CNG.setBackgroundColor(Color.TRANSPARENT)
            drawerLayout.closeDrawer(GravityCompat.START)

            llHeadingCard.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))

            nozzleList = mutableListOf(
                Nozzle("Nozzle 1", "Active"),
                Nozzle("Nozzle 2", "Active"),
                Nozzle("Nozzle 3", "Active")
            )
            adapter.updateData(nozzleList,"petrol")
        }
        CNG.setOnClickListener {
            fuelTypeHeading.text = "CNG"

            CNG.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            Petrol.setBackgroundColor(Color.TRANSPARENT)
            Diesel.setBackgroundColor(Color.TRANSPARENT)
            drawerLayout.closeDrawer(GravityCompat.START)

            llHeadingCard.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            nozzleList = mutableListOf(
                Nozzle("Nozzle 1", "Active")

            )
            adapter.updateData(nozzleList,"cng")
        }


        Diesel.performClick()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}