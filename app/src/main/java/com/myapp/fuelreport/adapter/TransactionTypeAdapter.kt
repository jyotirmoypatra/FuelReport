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
import com.myapp.fuelreport.activity.TransectionReportActivity
import com.myapp.fuelreport.model.TransactionType

class TransactionTypeAdapter(
    private val context: Context,
    private val transactionTypeList: List<TransactionType>,
    private val listener: TransectionReportActivity
): RecyclerView.Adapter<TransactionTypeAdapter.TransactionTypeViewHolder>() {
    private var selectedPosition: Int = 0

    init {
        // Trigger the click listener for the first item
        if (transactionTypeList.isNotEmpty()) {
            listener.onFuelItemClick(transactionTypeList[0].transactionTypeId,transactionTypeList[0].name)
        }
    }
    class TransactionTypeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val transactionTypeName: TextView = view.findViewById(R.id.transactionTypeName)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionTypeAdapter.TransactionTypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_type, parent, false)
        return TransactionTypeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TransactionTypeAdapter.TransactionTypeViewHolder,
        position: Int
    ) {
        val transactionType = transactionTypeList[position]
        holder.transactionTypeName.text = transactionType.name

        val currentPosition = holder.adapterPosition

        // Set the background color based on whether the item is selected
        if (currentPosition == selectedPosition) {
            holder.transactionTypeName.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray))
        } else {
            holder.transactionTypeName.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent)) // reset background
        }

        holder.transactionTypeName.setOnClickListener {
            // Call the listener to handle the click event
            listener.onFuelItemClick(transactionType.transactionTypeId,transactionType.name)
            selectedPosition = currentPosition
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int = transactionTypeList.size
}