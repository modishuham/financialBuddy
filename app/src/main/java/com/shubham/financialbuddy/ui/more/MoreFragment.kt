package com.shubham.financialbuddy.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentMoreBinding

class MoreFragment : BaseFragment() {

    private lateinit var mBinding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMoreBinding.inflate(inflater, container, false)
        return mBinding.root
    }
}