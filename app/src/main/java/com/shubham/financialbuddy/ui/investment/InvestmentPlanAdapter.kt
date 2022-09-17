package com.shubham.financialbuddy.ui.investment

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shubham.financialbuddy.R
import com.shubham.financialbuddy.analytics.AppAnalytics
import com.shubham.financialbuddy.databinding.ItemInvestmentPlanBinding
import com.shubham.financialbuddy.databinding.ItemInvestmentPlanTitleBinding

class InvestmentPlanAdapter(private val plansList: ArrayList<InvestmentPlan>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class PlanViewHolder(itemView: ItemInvestmentPlanBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
        fun bind(plan: InvestmentPlan) {
            binding.plan = plan

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvInvestmentPlanDescription.text = Html.fromHtml(plan.planDescription, Html.FROM_HTML_MODE_COMPACT)
            } else {
                binding.tvInvestmentPlanDescription.text = Html.fromHtml(plan.planDescription)
            }

            if(plan.isExpanded) {
                binding.tvInvestmentPlanDescription.visibility = View.VISIBLE
            } else {
                binding.tvInvestmentPlanDescription.visibility = View.GONE
            }

            binding.ivExpand.setOnClickListener {
                if (!plan.isExpanded) {
                    binding.tvInvestmentPlanDescription.visibility = View.VISIBLE
                    binding.ivExpand.setImageResource(R.drawable.ic_arrow_up)
                    plan.isExpanded = true
                    AppAnalytics.trackInvestmentPlanView(plan.planName)
                } else {
                    binding.tvInvestmentPlanDescription.visibility = View.GONE
                    binding.ivExpand.setImageResource(R.drawable.ic_arrow_down)
                    plan.isExpanded = false
                }
            }
        }
    }

    inner class TitleViewHolder(itemView: ItemInvestmentPlanTitleBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
        fun bind(plan: InvestmentPlan) {
            binding.plan = plan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TITLE -> {
                return TitleViewHolder(
                    ItemInvestmentPlanTitleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_PLAN -> {
                return PlanViewHolder(
                    ItemInvestmentPlanBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return PlanViewHolder(
                    ItemInvestmentPlanBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleViewHolder) {
            holder.bind(plansList[position])
        }
        if (holder is PlanViewHolder) {
            holder.bind(plansList[position])
        }
    }

    override fun getItemCount(): Int {
        return plansList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (plansList[position].viewType == "TITLE") {
            VIEW_TITLE
        } else {
            VIEW_PLAN
        }
    }

    companion object {
        const val VIEW_TITLE = 1
        const val VIEW_PLAN = 2
    }
}