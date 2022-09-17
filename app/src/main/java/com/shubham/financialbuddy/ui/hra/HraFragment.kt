package com.shubham.financialbuddy.ui.hra

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.analytics.AppAnalytics
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentHraBinding
import com.shubham.financialbuddy.utils.Utils
import kotlin.math.min

class HraFragment : BaseFragment() {

    private lateinit var mBinding: FragmentHraBinding
    private var cityType = "Non-metro city"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHraBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("Non-metro city", "Metro city")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (mBinding.etCityType.editText as? AutoCompleteTextView)?.setText("Non-metro city", false)
        (mBinding.etCityType.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (mBinding.etCityType.editText as? AutoCompleteTextView)?.addTextChangedListener {
            cityType = it.toString()
        }
        mBinding.btnCalculate.setOnClickListener {

            if (!validateFields())
                return@setOnClickListener

            AppAnalytics.trackCalculateHRA()
            AppAnalytics.trackScreenLaunch("HRA_screen")

            mBinding.clResult.visibility = View.VISIBLE

            val hraExempted = calculateHRA()
            val hraReceived = mBinding.etHraReceived.editText?.text.toString().toLong()
            mBinding.tvHraExemptedValue.text =
                "" + getText(R.string.Rs) + "" + Utils.rupeeFormat(hraExempted.toString())
            mBinding.tvHraTaxableValue.text =
                "" + getText(R.string.Rs) + "" + Utils.rupeeFormat((hraReceived - hraExempted).toString())
            showChart(hraReceived - hraExempted, hraReceived)
        }
    }

    private fun validateFields(): Boolean {
        mBinding.etBasicSalary.editText?.error = null
        mBinding.etHraReceived.editText?.error = null
        mBinding.etRentPaid.editText?.error = null
        mBinding.etDearnessAllowance.editText?.error = null

        if (mBinding.etBasicSalary.editText?.text.isNullOrEmpty()) {
            mBinding.etBasicSalary.editText?.error = "Please Enter Basic Salary"
            return false
        }

        if (mBinding.etHraReceived.editText?.text.isNullOrEmpty()) {
            mBinding.etHraReceived.editText?.error = "Please Enter HRA Received"
            return false
        }

        if (mBinding.etRentPaid.editText?.text.isNullOrEmpty()) {
            mBinding.etRentPaid.editText?.error = "Please Enter Rent"
            return false
        }

        if (mBinding.etDearnessAllowance.editText?.text.isNullOrEmpty()) {
            mBinding.etDearnessAllowance.editText?.error = "Please Enter Dearness Allowance"
            return false
        }
        return true
    }

    private fun calculateHRA(): Long {

        val basicSalary = mBinding.etBasicSalary.editText?.text.toString().toLong()
        val hraReceived = mBinding.etHraReceived.editText?.text.toString().toLong()
        val rentPaid = mBinding.etRentPaid.editText?.text.toString().toLong()
        val dearnessAllowance = mBinding.etDearnessAllowance.editText?.text.toString()
            .toLong()
        val tenPercentOfBaseSalary =
            ((mBinding.etBasicSalary.editText?.text.toString().toLong() * 10) / 100)
        val rentPaidMinusTenPercentSalary = if (dearnessAllowance > 0) {
            rentPaid - tenPercentOfBaseSalary + dearnessAllowance
        } else {
            rentPaid - tenPercentOfBaseSalary
        }
        val taxExemption: Long
        if (cityType == "Non-metro city") {
            var basicSalaryPercent = (basicSalary * 40) / 100
            if (dearnessAllowance > 0) {
                basicSalaryPercent += dearnessAllowance
            }
            taxExemption =
                min(hraReceived, min(basicSalaryPercent, rentPaidMinusTenPercentSalary))
        } else {
            var basicSalaryPercent = (basicSalary * 50) / 100
            if (dearnessAllowance > 0) {
                basicSalaryPercent += dearnessAllowance
            }
            taxExemption =
                min(hraReceived, min(basicSalaryPercent, rentPaidMinusTenPercentSalary))
        }
        return taxExemption
    }

    private fun showChart(
        taxableHRA: Long,
        totalHRA: Long
    ) {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = ""

        val tax = ((taxableHRA * 100) / totalHRA).toInt()

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["Taxable HRA"] = tax * 10
        typeAmountMap["Exempted HRA"] = 1000 - (tax * 10)

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#48AC42"))
        colors.add(Color.parseColor("#FF0970B5"))

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = Utils.convertDpToPixel(10F, requireContext())
        pieDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.transparent)
        //providing color list for coloring different entries
        pieDataSet.colors = colors
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true)
        mBinding.chart.description.isEnabled = false
        mBinding.chart.setHoleColor(Color.TRANSPARENT)
        mBinding.chart.transparentCircleRadius = 0F
        Toast.makeText(requireActivity(), "" + mBinding.chart.centerText, Toast.LENGTH_SHORT).show()
        mBinding.chart.data = pieData
        mBinding.chart.invalidate()
    }
}