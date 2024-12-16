package com.myapp.fuelreport.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper

import androidx.appcompat.widget.PopupMenu
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.myapp.fuelreport.R
import com.myapp.fuelreport.Util.ApiService
import com.myapp.fuelreport.Util.UserService
import com.myapp.fuelreport.model.Fuel
import com.myapp.fuelreport.model.FuelList
import com.myapp.fuelreport.model.getFuelAndTransactiobApiResponse
import retrofit2.Call
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {


    private lateinit var submitSalesReport: LinearLayout
    private lateinit var submitTransactionReport: LinearLayout
    private lateinit var viewReport: LinearLayout
    private lateinit var settingIcon: ImageView
    private var fuelListJson: String = ""
    private var transactionTypeListJson: String = ""
    private lateinit var progressBarBlockView: FrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var isRefreshData : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        submitSalesReport = findViewById(R.id.submitSalesReport)
        submitTransactionReport = findViewById(R.id.submitTransactionReport)
        viewReport = findViewById(R.id.viewReport)
        settingIcon = findViewById(R.id.settingsImgIcon)
        progressBarBlockView = findViewById(R.id.progressBarBlockView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        val orgId = sharedPreferences.getInt("orgId", -1)
        if (orgId != -1) {
            fetchFuelListAndDetails(orgId)
        }

        swipeRefreshLayout.setOnRefreshListener {
            // Stop the refreshing animation once done
            if (orgId != -1) {
                isRefreshData = true
                fetchFuelListAndDetails(orgId)
            }
            swipeRefreshLayout.isRefreshing = false
        }


        settingIcon.setOnClickListener {

            val popupMenu =
                PopupMenu(ContextThemeWrapper(this, R.style.CustomPopupMenu), settingIcon)
            popupMenu.menuInflater.inflate(R.menu.settings_menu, popupMenu.menu)

            // Handle menu item clicks
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.option1 -> {

                        true
                    }

                    R.id.option2 -> {
                        clearSharedPreferences()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }

            // Show the menu
            popupMenu.show()


        }


        submitSalesReport.setOnClickListener {
            if (fuelListJson.isNotEmpty()) {
                showDatePickerDialog(
                    headingText = "Please Select Date For Sales",
                    targetActivity = SalesReportActivity::class.java
                )
            } else {
                showAlert("Opps!", "Unable To Load Sales Data.Please Swipe down to Refresh..")
            }
        }
        submitTransactionReport.setOnClickListener {
            if(transactionTypeListJson.isNotEmpty()){
                showDatePickerDialog(
                    headingText = "Please Select Date For Transaction",
                    targetActivity = TransectionReportActivity::class.java
                )
            }else{
                showAlert("Opps!", "Unable To Load Transaction Data.Please Swipe down to Refresh..")
            }
        }
        viewReport.setOnClickListener {
            showDatePickerDialog(
                headingText = "Please Select Date For View Report",
                targetActivity = ViewReportActivity::class.java
            )
        }



    }


    private fun showDatePickerDialog(
        headingText: String,
        targetActivity: Class<*>
    ) {
        // Show a custom dialog
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_select_date)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Transparent background
        dialog.setCancelable(true)

        // Set heading text dynamically
        val heading = dialog.findViewById<TextView>(R.id.dialogHeading)
        val selectDateButton = dialog.findViewById<TextView>(R.id.selectDateButton)

        heading.text = headingText // Dynamically update the dialog heading

        selectDateButton.setOnClickListener {
            // Open DatePickerDialog when "Select Date" button is clicked
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                R.style.CustomDatePickerTheme, { _, selectedYear, selectedMonth, selectedDay ->
                    // Format the date to dd/MM/yyyy
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay)

                    val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val selectedDate = dateFormat.format(selectedCalendar.time) // Format the date

                    // Pass the selected date to the next activity
                    val intent = Intent(this, targetActivity)
                    intent.putExtra("selectedDate", selectedDate)

                    if (targetActivity == SalesReportActivity::class.java) {
                        intent.putExtra("fuelListJson", fuelListJson)
                    }else if(targetActivity == TransectionReportActivity::class.java){
                        intent.putExtra("transactionTypeListJson", transactionTypeListJson)
                    }
                    startActivity(intent)

                }, year, month, day
            )


            datePickerDialog.show()
            dialog.dismiss() // Dismiss the dialog after opening DatePickerDialog
        }

        dialog.show() // Show the dialog
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    private fun fetchFuelListAndDetails(orgId:Int) {

        progressBarBlockView.visibility = View.VISIBLE

        val userService = ApiService.getUserService()

        userService.getFuelTypesAndDetails(orgId)
            .enqueue(object : retrofit2.Callback<getFuelAndTransactiobApiResponse> {
                override fun onResponse(
                    call: Call<getFuelAndTransactiobApiResponse>,
                    response: retrofit2.Response<getFuelAndTransactiobApiResponse>
                ) {
                    if (response.isSuccessful) {
                        val content = response.body()
                        Log.d("api-response", "" + content)
                        val fuelList = content?.content?.fuelTypes ?: emptyList()
                        val transactionList = content?.content?.transactionTypes ?: emptyList()

                        // Serialize  JSON string
                        fuelListJson = Gson().toJson(fuelList)
                        transactionTypeListJson = Gson().toJson(transactionList)

                        Log.d("fuel-response", "" + fuelList)
                        if(isRefreshData){
                            isRefreshData = false
                            Toast.makeText(this@MainActivity, "Refresh Data Successfully", Toast.LENGTH_SHORT).show()
                        }

                    }
                    progressBarBlockView.visibility = View.GONE
                }

                override fun onFailure(call: Call<getFuelAndTransactiobApiResponse>, t: Throwable) {
                    // Handle the error
                    progressBarBlockView.visibility = View.GONE

                }
            })
    }

    fun clearSharedPreferences() {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }


    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog when the user clicks OK
            }
            .setCancelable(false)
            .show()
    }


}