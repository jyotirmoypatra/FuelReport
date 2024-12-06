package com.myapp.fuelreport

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button


import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper

import androidx.appcompat.widget.PopupMenu
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {


    private lateinit var submitSalesReport: LinearLayout
    private lateinit var submitTransactionReport: LinearLayout
    private lateinit var viewReport: LinearLayout
    private lateinit var settingIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        submitSalesReport = findViewById(R.id.submitSalesReport)
        submitTransactionReport = findViewById(R.id.submitTransactionReport)
        viewReport = findViewById(R.id.viewReport)
        settingIcon = findViewById(R.id.settingsImgIcon)


        settingIcon.setOnClickListener {
            // Create PopupMenu

            val popupMenu = PopupMenu(ContextThemeWrapper(this, R.style.CustomPopupMenu), settingIcon)
            popupMenu.menuInflater.inflate(R.menu.settings_menu, popupMenu.menu)

            // Handle menu item clicks
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.option1 -> {

                        true
                    }
                    R.id.option2 -> {
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
            showDatePickerDialog(
                headingText = "Please Select Date For Sales",
                targetActivity = SalesReportActivity::class.java
            )
        }

        submitTransactionReport.setOnClickListener {
            showDatePickerDialog(
                headingText = "Please Select Date For Transaction",
                targetActivity = TransectionReportActivity::class.java
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

            val datePickerDialog = DatePickerDialog(this,  R.style.CustomDatePickerTheme,{ _, selectedYear, selectedMonth, selectedDay ->
                // Format the date to dd/MM/yyyy
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val selectedDate = dateFormat.format(selectedCalendar.time) // Format the date

                // Pass the selected date to the next activity
                val intent = Intent(this, targetActivity)
                intent.putExtra("selectedDate", selectedDate)
                startActivity(intent)
            }, year, month, day)


            datePickerDialog.show()
            dialog.dismiss() // Dismiss the dialog after opening DatePickerDialog
        }

        dialog.show() // Show the dialog
    }



}