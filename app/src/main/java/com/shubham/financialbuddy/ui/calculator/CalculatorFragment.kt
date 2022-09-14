package com.shubham.financialbuddy.ui.calculator

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
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentCalculatorBinding
import com.shubham.financialbuddy.utils.CalculationHelper
import com.shubham.financialbuddy.utils.CalculatorType
import com.shubham.financialbuddy.utils.Utils


class CalculatorFragment : BaseFragment() {

    private lateinit var mBinding: FragmentCalculatorBinding
    private val args: CalculatorFragmentArgs by navArgs()
    private var mInvestedAmount = 5000L
    private var mRateOfReturn: Float = 12.0F
    private var mInvestmentDuration = 5
    private var mCompoundInYear = 12

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.etAmount.editText?.setText(mInvestedAmount.toString())
        mBinding.etReturnRate.editText?.setText(mRateOfReturn.toString())
        if (args.calculatorType == CalculatorType.FD.name ||
            args.calculatorType == CalculatorType.RD.name ||
            args.calculatorType == CalculatorType.EMI.name
        ) {
            mBinding.tvTimePeriod.text = "$mInvestmentDuration month"
        } else {
            mBinding.tvTimePeriod.text = "$mInvestmentDuration year"
        }

        when (args.calculatorType) {
            CalculatorType.SIP.name -> {
                mBinding.tvHeading.text = "SIP Calculator"
            }
            CalculatorType.LUMPSUM.name -> {
                mBinding.tvHeading.text = "Lumpsum Calculator"
                mBinding.etAmount.hint = "Total Investment"
            }
            CalculatorType.NSC.name -> {
                mBinding.tvHeading.text = "NSC Calculator"
                mBinding.etAmount.hint = "Total Investment"
                mBinding.sliderDuration.value = 5.0F
                mBinding.sliderDuration.isEnabled = false
                mBinding.etReturnRate.editText?.setText("6.8")
                mBinding.etReturnRate.isEnabled = false
            }
            CalculatorType.PPF.name -> {
                mBinding.tvHeading.text = "PPF Calculator"
                mBinding.etAmount.hint = "Yearly Investment"
                mBinding.sliderDuration.value = 15.0F
                mBinding.sliderDuration.valueFrom = 15.0F
                mBinding.tvTimePeriod.text = "15 year"
                mBinding.etReturnRate.editText?.setText("7.1")
                mBinding.etReturnRate.isEnabled = false
            }
            CalculatorType.FD.name -> {
                mBinding.tvHeading.text = "FD Calculator"
                mBinding.etAmount.hint = "Total Investment"
                mBinding.sliderDuration.value = 12.0F
                mBinding.sliderDuration.valueTo = 120.0F
                showCompoundingPeriod()
            }
            CalculatorType.PERSONAL_LOAN.name -> {
                mBinding.tvHeading.text = "Home Loan Calculator"
                mBinding.etAmount.hint = "Loan Amount"
            }
            CalculatorType.CAR_LOAN.name -> {
                mBinding.tvHeading.text = "Car Loan Calculator"
                mBinding.etAmount.hint = "Car Loan Amount"
                mBinding.sliderDuration.valueTo = 10.0F
            }
            CalculatorType.HOME_LOAN.name -> {
                mBinding.tvHeading.text = "Home Loan Calculator"
                mBinding.etAmount.hint = "Home Loan Amount"
                mBinding.sliderDuration.valueTo = 50.0F
            }
            CalculatorType.EMI.name -> {
                mBinding.tvHeading.text = "EMI Calculator"
                mBinding.etAmount.hint = "Total Purchased Amount"
                mBinding.sliderDuration.valueTo = 60.0F
            }
            CalculatorType.RD.name -> {
                mBinding.tvHeading.text = "RD Calculator"
                mBinding.etAmount.hint = "Monthly Investment"
                mBinding.sliderDuration.value = 12.0F
                mBinding.sliderDuration.valueTo = 120.0F
                showCompoundingPeriod()
            }
        }


        mBinding.sliderDuration.addOnChangeListener { _, value, _ ->
            mInvestmentDuration = value.toInt()
            if (args.calculatorType == CalculatorType.FD.name ||
                args.calculatorType == CalculatorType.RD.name ||
                args.calculatorType == CalculatorType.EMI.name
            ) {
                mBinding.tvTimePeriod.text = "${mInvestmentDuration}month"
            } else {
                mBinding.tvTimePeriod.text = "${mInvestmentDuration}Yr"
            }
        }

        mBinding.etAmount.editText?.addTextChangedListener {
            mBinding.etAmount.error = null
        }

        mBinding.etReturnRate.editText?.addTextChangedListener {
            mBinding.etReturnRate.error = null
        }

        mBinding.btnCalculate.setOnClickListener {
            calculate()
        }
    }

    private fun showCompoundingPeriod() {
        val items = listOf("Monthly", "Quarterly", "Half Yearly", "Yearly")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (mBinding.etCompoundPeriod.editText as? AutoCompleteTextView)?.setText("Yearly", false)
        (mBinding.etCompoundPeriod.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        mBinding.etCompoundPeriod.visibility = View.VISIBLE
        (mBinding.etCompoundPeriod.editText as? AutoCompleteTextView)?.addTextChangedListener {
            when (it.toString()) {
                "Monthly" -> {
                    mCompoundInYear = 12
                }
                "Quarterly" -> {
                    mCompoundInYear = 4
                }
                "Half Yearly" -> {
                    mCompoundInYear = 2
                }
                "Yearly" -> {
                    mCompoundInYear = 1
                }
            }
        }
    }

    private fun calculate() {

        if (mBinding.etAmount.editText?.text.isNullOrEmpty()) {
            mBinding.etAmount.error = "Please Enter Amount"
            return
        }

        if (mBinding.etReturnRate.editText?.text.isNullOrEmpty()) {
            mBinding.etReturnRate.error = "Please Enter Return Rate"
            return
        }

        mBinding.clResult.visibility = View.VISIBLE

        mRateOfReturn = mBinding.etReturnRate.editText?.text.toString().toFloat()
        mInvestedAmount = mBinding.etAmount.editText?.text.toString().toLong()
        when (args.calculatorType) {
            CalculatorType.SIP.name -> {
                val finalAmount =
                    CalculationHelper.calculateSip(
                        mInvestedAmount,
                        mRateOfReturn,
                        mInvestmentDuration
                    )
                setResults(mInvestedAmount * mInvestmentDuration * 12, finalAmount)
            }
            CalculatorType.LUMPSUM.name,
            CalculatorType.NSC.name -> {
                val finalAmount =
                    CalculationHelper.calculateLumpSum(
                        mInvestedAmount,
                        mInvestmentDuration,
                        mRateOfReturn
                    )
                setResults(mInvestedAmount, finalAmount)
            }
            CalculatorType.PPF.name -> {
                val finalAmount =
                    CalculationHelper.calculatePPF(
                        mInvestedAmount,
                        mInvestmentDuration,
                        mRateOfReturn
                    )
                setResults(mInvestedAmount * mInvestmentDuration, finalAmount)
            }
            CalculatorType.FD.name -> {
                val finalAmount =
                    CalculationHelper.calculateFD(
                        mInvestedAmount,
                        mInvestmentDuration,
                        mRateOfReturn,
                        mCompoundInYear
                    )
                setResults(mInvestedAmount, finalAmount)
            }
            CalculatorType.HOME_LOAN.name,
            CalculatorType.CAR_LOAN.name,
            CalculatorType.PERSONAL_LOAN.name -> {
                val monthlyEMI =
                    CalculationHelper.calculateHomeLoan(
                        mInvestedAmount,
                        mInvestmentDuration,
                        mRateOfReturn,
                    )
                val finalAmount = (monthlyEMI * mInvestmentDuration * 12).toLong()
                setResults(mInvestedAmount, finalAmount)
            }
            CalculatorType.EMI.name -> {
                val monthlyEMI =
                    CalculationHelper.calculateHomeLoan(
                        mInvestedAmount,
                        mInvestmentDuration,
                        mRateOfReturn,
                    )
                Toast.makeText(requireActivity(), "" + monthlyEMI, Toast.LENGTH_LONG).show()
            }
            CalculatorType.RD.name -> {
                val interestEarned =
                    CalculationHelper.calculateRD(
                        mInvestedAmount,
                        mInvestmentDuration,
                        mRateOfReturn,
                        mCompoundInYear
                    )
                val investedAmount = mInvestedAmount * mInvestmentDuration
                setResults(investedAmount, interestEarned)
            }
        }
    }

    private fun setResults(
        investedAmount: Long,
        finalAmount: Long
    ) {
        mBinding.tvTotalValue.text =
            "" + getText(R.string.Rs) + "" + Utils.rupeeFormat(finalAmount.toString())
        mBinding.tvInvestedAmount.text =
            "" + getText(R.string.Rs) + "" + Utils.rupeeFormat(investedAmount.toString())
        mBinding.tvEstReturns.text =
            "" + getText(R.string.Rs) + "" + Utils.rupeeFormat((finalAmount - investedAmount).toString())
        showChart(finalAmount - investedAmount, finalAmount)
    }

    private fun showChart(
        interestAmount: Long,
        finalAmount: Long
    ) {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = ""

        val totalProfitPercentage = ((interestAmount * 100) / finalAmount).toInt()

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["Profit"] = totalProfitPercentage * 10
        typeAmountMap["Investment"] = 1000 - (totalProfitPercentage * 10)

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#304567"))
        colors.add(Color.parseColor("#309967"))

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