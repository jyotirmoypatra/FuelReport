package com.myapp.fuelreport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapp.fuelreport.R
import com.myapp.fuelreport.model.TransactionType

class TransactionTypeAdapter(
    private val context: Context,
    private val transactionTypeList: List<TransactionType>,
): RecyclerView.Adapter<TransactionTypeAdapter.TransactionTypeViewHolder>() {

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

    }

    override fun getItemCount(): Int = transactionTypeList.size
}