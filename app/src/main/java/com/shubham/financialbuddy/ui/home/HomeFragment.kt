package com.shubham.financialbuddy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentHomeBinding
import com.shubham.financialbuddy.utils.CalculationHelper
import com.shubham.financialbuddy.utils.GridSpaceItemDecoration
import com.shubham.financialbuddy.utils.Utils

class HomeFragment : BaseFragment() {

    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rvCalculator.addItemDecoration(
            GridSpaceItemDecoration(
                Utils.convertDpToPixel(
                    3F,
                    requireContext()
                ).toInt()
            )
        )

        mBinding.btnGenerateRentReceipt.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_rentReceiptListFragment)
        }

        mBinding.btnHraCalculation.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_hraFragment)
        }

        mBinding.rvCalculator.adapter = CalculatorAdapter(CalculationHelper.getHomeCalculators())
        mBinding.rvCalculator.setHasFixedSize(true)
    }


}