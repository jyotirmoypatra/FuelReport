package com.myapp.fuelreport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val context: Context,
                         private val transactionList: MutableList<TransactionModel>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       val amountEdtTxt: EditText = view.findViewById(R.id.amountEdtTxt)
       val NoteEdtTxt: EditText = view.findViewById(R.id.NoteEdtTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {

        val transaction = transactionList[position]
        //holder.amountEdtTxt.setText(transaction.amount)
       // holder.NoteEdtTxt.setText(transaction.notes)



    }

    fun updateData(newNozzleList: List<TransactionModel>) {

        transactionList.clear()


        transactionList.addAll(newNozzleList)

        notifyDataSetChanged()
    }
}

