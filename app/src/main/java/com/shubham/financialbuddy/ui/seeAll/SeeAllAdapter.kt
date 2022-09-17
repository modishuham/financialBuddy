package com.shubham.financialbuddy.ui.seeAll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.analytics.AppAnalytics
import com.shubham.financialbuddy.ui.home.Calculator

class SeeAllAdapter(private val calculatorList: ArrayList<Calculator>) :
    RecyclerView.Adapter<SeeAllAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_calculator_name)
        val description: TextView = itemView.findViewById(R.id.tv_calculator_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_see_all_calculator, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = calculatorList[position].name
        holder.description.text = calculatorList[position].description
        holder.itemView.setOnClickListener {
            AppAnalytics.trackCalculatorOpen(calculatorList[position].name)
            val action =
                SeeAllFragmentDirections.actionSeeAllFragmentToCalculatorFragment(calculatorList[position].type)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return calculatorList.size
    }
}