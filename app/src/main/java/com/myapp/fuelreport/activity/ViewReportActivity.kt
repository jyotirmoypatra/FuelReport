package com.myapp.fuelreport.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
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
import androidx.fragment.app.Fragment
import com.myapp.fuelreport.R
import com.myapp.fuelreport.fragment.ExportFragment
import com.myapp.fuelreport.fragment.SaleFragment
import com.myapp.fuelreport.fragment.TransactionFragment

class ViewReportActivity : AppCompatActivity() {

    private lateinit var menuBtnReportView: ImageView
    private lateinit var viewReport_drawer_layout: DrawerLayout
    private lateinit var viewReportHeading: TextView
    private lateinit var SALE: TextView
    private lateinit var TRANSACTION: TextView
    private lateinit var dateTxtReport: TextView
    private lateinit var llDate: LinearLayout
    private lateinit var EXPORT: TextView
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_view_report)

        menuBtnReportView = findViewById(R.id.menuBtnReportView)
        viewReport_drawer_layout = findViewById(R.id.viewReport_drawer_layout)
        viewReportHeading = findViewById(R.id.viewReportHeading)
        SALE = findViewById(R.id.SALE)
        TRANSACTION = findViewById(R.id.TRANSACTION)
        EXPORT = findViewById(R.id.EXPORT)
        dateTxtReport = findViewById(R.id.dateTxtReport)
        frameLayout = findViewById(R.id.frameLayout)
        llDate = findViewById(R.id.llDate)

        val selectedDate = intent.getStringExtra("selectedDate")
        dateTxtReport.text = selectedDate ?: "No Date Selected"

        menuBtnReportView.setOnClickListener {
            if (viewReport_drawer_layout.isDrawerOpen(GravityCompat.START)) {
                viewReport_drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                viewReport_drawer_layout.openDrawer(GravityCompat.START)

            }
        }

        SALE.setOnClickListener {
            loadFragment(SaleFragment(),selectedDate ?: "")
            viewReportHeading.text = "SALE REPORT"
            SALE.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            TRANSACTION.setBackgroundColor(Color.TRANSPARENT)
            EXPORT.setBackgroundColor(Color.TRANSPARENT)
            viewReport_drawer_layout.closeDrawer(GravityCompat.START)
            llDate.visibility = View.VISIBLE

        }
        TRANSACTION.setOnClickListener {
            loadFragment(TransactionFragment(),selectedDate ?: "")
            viewReportHeading.text = "TRANSACTION REPORT"
            TRANSACTION.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            SALE.setBackgroundColor(Color.TRANSPARENT)
            EXPORT.setBackgroundColor(Color.TRANSPARENT)
            viewReport_drawer_layout.closeDrawer(GravityCompat.START)
            llDate.visibility = View.VISIBLE
        }
        EXPORT.setOnClickListener {
            loadFragment(ExportFragment(),selectedDate ?: "")
            viewReportHeading.text = "EXPORT REPORT"
            EXPORT.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGray))
            SALE.setBackgroundColor(Color.TRANSPARENT)
            TRANSACTION.setBackgroundColor(Color.TRANSPARENT)
            viewReport_drawer_layout.closeDrawer(GravityCompat.START)

            llDate.visibility = View.GONE
        }

        SALE.performClick()

    }
    private fun loadFragment(fragment: Fragment, date: String) {
        val bundle = Bundle()
        bundle.putString("DATE_KEY", date)

        // Set the arguments for the fragment
        fragment.arguments = bundle

        // Load the fragment with animations
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,  // Animation for fragment entry
                android.R.anim.slide_out_right, // Animation for fragment exit
                android.R.anim.slide_in_left,  // Animation for pop-in (back stack)
                android.R.anim.slide_out_right // Animation for pop-out (back stack)
            )
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null) // Optional: Adds the transaction to the back stack
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }


}