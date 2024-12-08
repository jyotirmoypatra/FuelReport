package com.myapp.fuelreport.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myapp.fuelreport.model.Nozzle
import com.myapp.fuelreport.R
import com.myapp.fuelreport.adapter.FuelTypeAdapter
import com.myapp.fuelreport.adapter.NozzleAdapter
import com.myapp.fuelreport.model.Fuel

class SalesReportActivity : AppCompatActivity() {

    private lateinit var menuBtn: ImageView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var nozzelRecyclerView: RecyclerView
    private lateinit var fuelTypeRecyclerView: RecyclerView
    private lateinit var fuelTypeHeading: TextView
    private lateinit var llHeadingCard: LinearLayout
    private lateinit var dateTextView: TextView
    private lateinit var main_content: LinearLayout

    private var nozzleList : List<Nozzle> = emptyList()
    private var fuelList: List<Fuel> = emptyList()
    private lateinit var nozzleAdapter: NozzleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sales_report)

        main_content = findViewById(R.id.main_content)
        menuBtn = findViewById(R.id.menuBtn)
        drawerLayout = findViewById(R.id.drawer_layout)
        nozzelRecyclerView = findViewById(R.id.nozzelRecyclerView)
        fuelTypeRecyclerView = findViewById(R.id.fuelTypeRecyclerView)
        fuelTypeHeading = findViewById(R.id.fuelTypeHeading)
        llHeadingCard = findViewById(R.id.llHeadingCard)
        dateTextView = findViewById(R.id.dateTextView)


        val selectedDate = intent.getStringExtra("selectedDate")
        dateTextView.text = selectedDate ?: "No Date Selected"

        val fuelListJson = intent.getStringExtra("fuelListJson")

        if (!fuelListJson.isNullOrEmpty()) {
            // Deserialize the JSON string back into a List<Fuel>
            val fuelListType = object : TypeToken<List<Fuel>>() {}.type
            fuelList = Gson().fromJson(fuelListJson, fuelListType)
        } else {
            Log.d("fuel-list", "fuelList is empty or null")
        }

        menuBtn.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)

            }
        }


        // Set up RecyclerView Nozzle
        nozzleAdapter = NozzleAdapter(this, nozzleList)
        nozzelRecyclerView.layoutManager = LinearLayoutManager(this)
        nozzelRecyclerView.adapter = nozzleAdapter

        // Set up RecyclerView Fuel
        val fuelAdapter = FuelTypeAdapter(this, fuelList, this)
        fuelTypeRecyclerView.layoutManager = LinearLayoutManager(this)
        fuelTypeRecyclerView.adapter = fuelAdapter


    }


    fun onFuelItemClick(fuelId: Long) {
        Log.d("click-item", "" + fuelId)

        val clickedFuel = fuelList.firstOrNull { it.fuelTypeId == fuelId}

        nozzleList = clickedFuel?.nozzels ?: emptyList()
        nozzleAdapter.updateNozzles(nozzleList)

        fuelTypeHeading.text = clickedFuel?.name

        drawerLayout.closeDrawer(GravityCompat.START)

    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}