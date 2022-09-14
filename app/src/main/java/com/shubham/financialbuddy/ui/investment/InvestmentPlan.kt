package com.shubham.financialbuddy.ui.investment

data class InvestmentPlan(
    val viewType: String,
    val planName: String,
    val planDescription: String,
    val riskType: String,
    val expectedReturns: String,
    var isExpanded: Boolean
)
