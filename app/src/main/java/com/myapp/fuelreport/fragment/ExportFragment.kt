package com.myapp.fuelreport.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.myapp.fuelreport.R
import com.myapp.fuelreport.activity.TransectionReportActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExportFragment : Fragment() {

    private var date: String? = null
    private lateinit var txtStartDate: TextView
    private lateinit var txtEndDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the date argument
        arguments?.let {
            date = it.getString("DATE_KEY")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_export, container, false)

         txtStartDate = view.findViewById(R.id.txtStartDate)
         txtEndDate = view.findViewById(R.id.txtEndDate)
        val startDateCard: CardView = view.findViewById(R.id.startDateCard)
        val endDateCard: CardView = view.findViewById(R.id.endDateCard)

        startDateCard.setOnClickListener {
            showDatePickerDialog(
                txtStartDate
            )
        }

        endDateCard.setOnClickListener {
            showDatePickerDialog(
                txtEndDate
            )
        }



        return view
    }

    private fun showDatePickerDialog(
        selectedTextView: TextView

    ) {
        // Open DatePickerDialog when "Select Date" button is clicked
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            R.style.CustomDatePickerTheme,{ _, selectedYear, selectedMonth, selectedDay ->
                // Format the date to dd/MM/yyyy
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val selectedDate = dateFormat.format(selectedCalendar.time) // Format the date


                if (selectedTextView == txtStartDate) {
                    selectedTextView.text = getString(R.string.start_date_text, selectedDate)
                } else if (selectedTextView == txtEndDate) {
                    selectedTextView.text = getString(R.string.end_date_text, selectedDate)
                }



            }, year, month, day)


        datePickerDialog.show()
    }

}