package com.myapp.fuelreport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val context: Context,
                         private val transactionList: MutableList<TransactionModel>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       val amountEdtTxt: EditText = view.findViewById(R.id.amountEdtTxt)
       val NoteEdtTxt: EditText = view.findViewById(R.id.NoteEdtTxt)
       val deleteRow: ImageView = view.findViewById(R.id.deleteRow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]

        holder.deleteRow.setOnClickListener {
            // Remove the item from the list
            transactionList.removeAt(position)

            // Notify the adapter about the item being removed
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, transactionList.size) // To update item positions
        }

    }

}

