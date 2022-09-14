package com.shubham.financialbuddy.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shubham.financialbuddy.databinding.ItemCalculatorBinding
import com.shubham.financialbuddy.databinding.ItemSeeAllBinding

class CalculatorAdapter(private val calculatorList: ArrayList<Calculator>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class CalculatorViewHolder(itemView: ItemCalculatorBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        fun bind(calculator: Calculator) {
            binding.calculator = calculator

            binding.root.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToCalculatorFragment(calculator.type)
                it.findNavController().navigate(action)
            }
        }
    }

    inner class SeeAllViewHolder(itemView: ItemSeeAllBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        fun bind(calculator: Calculator) {
            binding.root.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSeeAllFragment()
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            SEE_ALL_CARD -> {
                val binding: ItemSeeAllBinding =
                    ItemSeeAllBinding.inflate(LayoutInflater.from(parent.context))
                return SeeAllViewHolder(binding)
            }
            CALCULATOR_CARD -> {
                val binding: ItemCalculatorBinding =
                    ItemCalculatorBinding.inflate(LayoutInflater.from(parent.context))
                return CalculatorViewHolder(binding)
            }
            else -> {
                val binding: ItemCalculatorBinding =
                    ItemCalculatorBinding.inflate(LayoutInflater.from(parent.context))
                return CalculatorViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SeeAllViewHolder) {
            holder.bind(calculatorList[position])
        }
        if (holder is CalculatorViewHolder) {
            holder.bind(calculatorList[position])
        }
    }

    override fun getItemCount(): Int {
        return calculatorList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (calculatorList[position].name == "SEE_ALL") {
            SEE_ALL_CARD
        } else {
            CALCULATOR_CARD
        }
    }

    companion object {
        const val CALCULATOR_CARD = 1
        const val SEE_ALL_CARD = 2
    }

}