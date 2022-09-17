package com.shubham.financialbuddy.ui.seeAll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.financialbuddy.analytics.AppAnalytics
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentSeeAllBinding
import com.shubham.financialbuddy.utils.CalculationHelper

class SeeAllFragment : BaseFragment() {

    private lateinit var mBinding: FragmentSeeAllBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSeeAllBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppAnalytics.trackScreenLaunch("See_all_screen")

        mBinding.rvAllCalculator.adapter = SeeAllAdapter(CalculationHelper.getAllCalculators())

        AppAnalytics.trackSeeAllClick()
    }
}