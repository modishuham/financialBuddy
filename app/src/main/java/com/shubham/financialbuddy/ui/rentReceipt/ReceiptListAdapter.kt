package com.shubham.financialbuddy.ui.rentReceipt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.rajat.pdfviewer.PdfViewerActivity
import com.shubham.financialbuddy.R
import java.io.File

class ReceiptListAdapter(private val files: ArrayList<File>) :
    RecyclerView.Adapter<ReceiptListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_receipt_name)
        private val menu: ImageView = itemView.findViewById(R.id.iv_more)

        fun bind(file: File) {
            name.text = file.name
            itemView.setOnClickListener {
                it.context.startActivity(
                    PdfViewerActivity.launchPdfFromPath(
                        it.context,
                        file.path,
                        file.name,
                        "",
                        enableDownload = false
                    )
                )
            }
            menu.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)
                popup.inflate(R.menu.menu_delete_share)
                popup.setOnMenuItemClickListener {
                    if (it.itemId == R.id.menu_delete) {
                        val fileDir: String =
                            "" + view.context.getExternalFilesDir(null) + "/RentReceipts"
                        val mFile = File(fileDir, file.name)
                        if (mFile.exists()) {
                            mFile.delete()
                            files.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            notifyItemRangeChanged(adapterPosition, files.size)
                        }
                    } else {
                        sharePDF(view.context, file.path)
                    }
                    return@setOnMenuItemClickListener true
                }
                popup.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_rent_receipt,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(files[position])
    }

    override fun getItemCount(): Int {
        return files.size
    }

    private fun sharePDF(context: Context, path: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            val uri = FileProvider.getUriForFile(
                context,
                "com.shubham.financialbuddy.provider",
                File(path)
            )
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/*"
            context.startActivity(Intent.createChooser(intent, "Share"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}