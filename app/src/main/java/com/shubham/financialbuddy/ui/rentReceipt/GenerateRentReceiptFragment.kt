package com.shubham.financialbuddy.ui.rentReceipt

import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentGenerateRentReceiptBinding
import com.shubham.financialbuddy.model.RentReceiptData
import com.shubham.financialbuddy.utils.Utils
import java.io.File
import java.io.FileOutputStream


class GenerateRentReceiptFragment : BaseFragment() {

    private lateinit var mBinding: FragmentGenerateRentReceiptBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentGenerateRentReceiptBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnGenerate.setOnClickListener {
            if (validateInformation()) {
                generateRentReceipt()
            }
        }

        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
        materialDateBuilder.setTitleText("Select Date")
        val materialDatePickerStartDate = materialDateBuilder.build()
        val materialDatePickerEndDate = materialDateBuilder.build()

        mBinding.etStartDate.editText?.setOnClickListener {
            materialDatePickerStartDate.show(childFragmentManager, "MATERIAL_DATE_PICKER");
        }

        mBinding.etEndDate.editText?.setOnClickListener {
            materialDatePickerEndDate.show(childFragmentManager, "MATERIAL_DATE_PICKER");
        }

        materialDatePickerStartDate.addOnPositiveButtonClickListener {
            val startDate =
                Utils.convertDateToMonthFormat(materialDatePickerStartDate.headerText.toString())
            mBinding.etStartDate.editText?.setText(startDate)
        }

        materialDatePickerEndDate.addOnPositiveButtonClickListener {
            val endDate =
                Utils.convertDateToMonthFormat(materialDatePickerEndDate.headerText.toString())
            mBinding.etEndDate.editText?.setText(endDate)
        }
    }

    private fun validateInformation(): Boolean {
        mBinding.etName.error = null
        mBinding.etMonthlyRent.error = null
        mBinding.etLandlordName.error = null
        mBinding.etAddress.error = null
        mBinding.etStartDate.error = null
        mBinding.etEndDate.error = null

        if (mBinding.etName.editText?.text.isNullOrEmpty()) {
            mBinding.etName.error = "Please Enter Your Name"
            return false
        }

        if (mBinding.etMonthlyRent.editText?.text.isNullOrEmpty()) {
            mBinding.etMonthlyRent.error = "Please Enter Monthly Rent"
            return false
        }

        if (mBinding.etLandlordName.editText?.text.isNullOrEmpty()) {
            mBinding.etLandlordName.error = "Please Enter House Owner's Name"
            return false
        }

        if (mBinding.etAddress.editText?.text.isNullOrEmpty()) {
            mBinding.etAddress.error = "Please Enter Address"
            return false
        }

        if (mBinding.etStartDate.editText?.text.isNullOrEmpty()) {
            mBinding.etStartDate.error = "Please Enter Date"
            return false
        }

        if (mBinding.etEndDate.editText?.text.isNullOrEmpty()) {
            mBinding.etEndDate.error = "Please Enter Date"
            return false
        }

        return true
    }

    private fun generateRentReceipt() {

        var receiptTypeMonthly: Boolean = false
        if (mBinding.radioButtonMonthly.isChecked) {
            receiptTypeMonthly = true
        }

        val name = mBinding.etName.editText?.text.toString()
        val monthlyRent = mBinding.etMonthlyRent.editText?.text.toString()
        val myPan = mBinding.etMyPan.editText?.text.toString()
        val landlordName = mBinding.etLandlordName.editText?.text.toString()
        val landlordPan = mBinding.etLandlordPan.editText?.text.toString()
        val address = mBinding.etAddress.editText?.text.toString()

        val month: ArrayList<RentReceiptData> = if (mBinding.radioButtonMonthly.isChecked) {
            Utils.getMonthlyData(
                Utils.getMonthsBetweenDates(
                    mBinding.etStartDate.editText?.text.toString(),
                    mBinding.etEndDate.editText?.text.toString()
                )
            )
        } else {
            Utils.getQuarterlyMonth(
                Utils.getMonthsBetweenDates(
                    mBinding.etStartDate.editText?.text.toString(),
                    mBinding.etEndDate.editText?.text.toString()
                )
            )
        }


        // on below line we are initializing our bitmap and scaled bitmap.
        val bmp =
            BitmapFactory.decodeResource(requireActivity().resources, R.drawable.app_logo_circle)
        val appLogo = Bitmap.createScaledBitmap(bmp, 40, 40, false)


        // declaring width and height
        // for our PDF file.
        val pageHeight = 1120
        val pageWidth = 792

        var increment = 0

        // creating an object variable
        // for our PDF document.
        val pdfDocument = PdfDocument()

        val paint = Paint()

        val title = Paint()
        title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        title.textSize = 12F
        title.color = ContextCompat.getColor(requireActivity(), R.color.black)

        val valuePaint = Paint()
        valuePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        valuePaint.textSize = 12F
        valuePaint.color = ContextCompat.getColor(requireActivity(), R.color.black)

        val linePaint = Paint()

        val centerPaint = Paint()
        centerPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        centerPaint.textSize = 16F
        centerPaint.color = ContextCompat.getColor(requireActivity(), R.color.black)

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        val myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        var receiptNo = 1
        month.chunked(2).forEach {
            // below line is used for setting
            // start page for our PDF file.
            val myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

            increment = 100
            // creating a variable for canvas
            // from our page of PDF.
            val canvas: Canvas = myPage.canvas

            canvas.drawText("RENT RECEIPT", 340F, 80F, centerPaint)
            canvas.drawText("Powered By FinancialBuddy", 490F, 1000F, centerPaint)
            canvas.drawBitmap(appLogo, 700F, 975F, paint)

            for (item in it) {

                val rect = Rect(40, 60 + increment, 752, 250 + increment)
                paint.style = Paint.Style.STROKE
                paint.color = Color.BLACK
                canvas.drawRect(rect, paint)

                canvas.drawText("RENT RECEIPT", 60F, 80F + increment, title)
                canvas.drawText("" + item.monthName, 60F, 98F + increment, valuePaint)
                canvas.drawText("Receipt $receiptNo", 680F, 80F + increment, title)
                receiptNo++

                canvas.drawText("Received From(Tenant):", 60F, 140F + increment, title)
                canvas.drawText(name, 196F, 138F + increment, valuePaint)
                canvas.drawLine(196F, 140F + increment, 740F, 140F + increment, linePaint)

                canvas.drawText("Address:", 60F, 165F + increment, title)
                canvas.drawText(address, 116F, 163F + increment, valuePaint)
                canvas.drawLine(116F, 165F + increment, 740F, 165F + increment, linePaint)

                canvas.drawText("Monthly Rent(INR):", 60F, 190F + increment, title)
                canvas.drawText(monthlyRent, 170F, 188F + increment, valuePaint)
                canvas.drawLine(170F, 190F + increment, 290F, 190F + increment, linePaint)

                canvas.drawText("Total Rent(INR):", 300F, 190F + increment, title)
                if (receiptTypeMonthly) {
                    canvas.drawText(monthlyRent, 390F, 188F + increment, valuePaint)
                } else {
                    canvas.drawText(
                        (monthlyRent.toInt() * 3).toString(),
                        390F,
                        188F + increment,
                        valuePaint
                    )
                }
                canvas.drawLine(390F, 190F + increment, 740F, 190F + increment, linePaint)

                canvas.drawText("Landlord's Name:", 60F, 215F + increment, title)
                canvas.drawText(landlordName, 160F, 213F + increment, valuePaint)
                canvas.drawLine(160F, 215F + increment, 740F, 215F + increment, linePaint)

                canvas.drawText("Signature Of Landlord", 580F, 330F + increment, title)
                val rect2 = Rect(702, 270 + increment, 752, 330 + increment)
                canvas.drawText("Revenue", 704F, 300F + increment, valuePaint)
                canvas.drawText("Stamp", 710F, 314F + increment, valuePaint)
                canvas.drawRect(rect2, paint)

                if (landlordPan.isNotEmpty()) {
                    canvas.drawText("PAN ($landlordPan)", 580F, 344F + increment, valuePaint)
                }


                increment += 400

            }
            // after adding all attributes to our
            // PDF file we will be finishing our page.
            pdfDocument.finishPage(myPage)

        }

        val file =
            File(
                requireActivity().getExternalFilesDir("RentReceipts"),
                "Rent_Receipt_" + System.currentTimeMillis() + ".pdf"
            )

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(FileOutputStream(file))

            // on below line we are displaying a toast message as PDF file generated..
            Toast.makeText(requireActivity(), "Rent Receipt Generated..", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()

            // on below line we are displaying a toast message as fail to generate PDF
            Toast.makeText(requireActivity(), "Something Went Wrong..", Toast.LENGTH_SHORT)
                .show()
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()
    }
}