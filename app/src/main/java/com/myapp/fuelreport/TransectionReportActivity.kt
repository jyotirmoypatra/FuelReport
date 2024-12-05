package com.myapp.fuelreport

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class TransectionReportActivity : AppCompatActivity() {
    private lateinit var menuBtnTransaction: ImageView
    private lateinit var Transaction_drawer_layout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_transection_report)


        menuBtnTransaction = findViewById(R.id.menuBtnTransaction)
        Transaction_drawer_layout = findViewById(R.id.Transaction_drawer_layout)


        menuBtnTransaction.setOnClickListener {
            if (Transaction_drawer_layout.isDrawerOpen(GravityCompat.START)) {
                Transaction_drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                Transaction_drawer_layout.openDrawer(GravityCompat.START)
            }
        }
    }
}