package com.shubham.financialbuddy.ui.rentReceipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentRentReceiptListBinding
import java.io.File

class RentReceiptListFragment : BaseFragment() {

    private lateinit var mBinding: FragmentRentReceiptListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRentReceiptListBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnGenerateRentReceipt.setOnClickListener {
            findNavController().navigate(R.id.action_rentReceiptListFragment_to_generateRentReceiptFragment)
        }
        val storageDir: String =
            "" + requireContext().getExternalFilesDir(null) + "/RentReceipts"
        val mFolder = File(storageDir)
        if (!mFolder.exists()) {
            mFolder.mkdir()
        }
        val files = mFolder.listFiles { _, name ->
            name.endsWith(".pdf")
        }
        files?.let {
            if (it.isNotEmpty()) {
                mBinding.rvRentReceipt.adapter = ReceiptListAdapter(it.toCollection(ArrayList()))
            }
        }
    }
}