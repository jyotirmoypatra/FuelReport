package com.myapp.fuelreport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myapp.fuelreport.model.Nozzle
import com.myapp.fuelreport.R

class NozzleAdapter(
    private val context: Context,
    private var nozzleList: List<Nozzle>) :
    RecyclerView.Adapter<NozzleAdapter.NozzleViewHolder>() {

    private var fuelType: String? = null
    class NozzleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nozzelName: TextView = view.findViewById(R.id.nozzelName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NozzleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nozzle, parent, false)
        return NozzleViewHolder(view)
    }

    override fun onBindViewHolder(holder: NozzleViewHolder, position: Int) {
        val nozzle = nozzleList[position]
        holder.nozzelName.text = nozzle.nozzelName

//        if (fuelType == "diesel") {  // Example condition for changing background color
//            holder.nozzelName.setBackgroundColor(ContextCompat.getColor(context,
//                R.color.lightYellow
//            ))
//        } else if(fuelType == "petrol"){
//            holder.nozzelName.setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
//        }else if(fuelType == "cng"){
//            holder.nozzelName.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
//        }

    }

    override fun getItemCount(): Int = nozzleList.size

    fun updateNozzles(newNozzles: List<Nozzle>) {
        nozzleList = newNozzles
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

}