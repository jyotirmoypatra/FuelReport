package com.myapp.fuelreport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myapp.fuelreport.R
import com.myapp.fuelreport.activity.SalesReportActivity
import com.myapp.fuelreport.model.Fuel

class FuelTypeAdapter(
    private val context: Context,
    private val fuelList: List<Fuel>,
    private val listener: SalesReportActivity
) :
    RecyclerView.Adapter<FuelTypeAdapter.FuelViewHolder>() {
    private var selectedPosition: Int = 0 // To track the selected item
    init {
        // Trigger the click listener for the first item
        if (fuelList.isNotEmpty()) {
            listener.onFuelItemClick(fuelList[0].fuelTypeId)
        }
    }

    class FuelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fuelName: TextView = view.findViewById(R.id.fuelName)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelTypeAdapter.FuelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fuel_type, parent, false)
        return FuelViewHolder(view)
    }

//    override fun onBindViewHolder(
//        holder: FuelTypeAdapter.FuelViewHolder,
//        position: Int
//    ) {
//        val fuel = fuelList[position]
//        holder.fuelName.text = fuel.name
//
//        // Set the background color based on whether the item is selected
//        if (position == selectedPosition) {
//            holder.fuelName.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray))
//        } else {
//            holder.fuelName.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent)) // reset background
//        }
//
//        holder.fuelName.setOnClickListener {
//            // Call the listener to handle the click event
//            listener.onFuelItemClick(fuel.fuelTypeId)
//            selectedPosition = position
//            notifyDataSetChanged()
//        }
//
//    }
    override fun onBindViewHolder(
        holder: FuelTypeAdapter.FuelViewHolder,
        position: Int
    ) {
        val fuel = fuelList[position]
        holder.fuelName.text = fuel.name

        // Use adapterPosition instead of position
        val currentPosition = holder.adapterPosition

        // Set the background color based on whether the item is selected
        if (currentPosition == selectedPosition) {
            holder.fuelName.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray))
        } else {
            holder.fuelName.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent)) // reset background
        }

        holder.fuelName.setOnClickListener {
            // Call the listener to handle the click event
            listener.onFuelItemClick(fuel.fuelTypeId)
            selectedPosition = currentPosition
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int = fuelList.size
}