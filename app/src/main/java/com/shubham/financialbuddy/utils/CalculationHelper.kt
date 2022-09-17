package com.shubham.financialbuddy.utils

import com.shubham.financialbuddy.ui.home.Calculator
import kotlin.math.pow

object CalculationHelper {

    fun calculateSip(
        amount: Long,
        interestRate: Float,
        durationInYears: Int
    ): Long {
        //M = P × ({[1 + i]n – 1} / i) × (1 + i).
        val durationInMonths = durationInYears * 12
        val periodRateOfInterest: Float = (interestRate / (12 * 100))
        val mid = (((1 + periodRateOfInterest).toDouble()
            .pow((durationInMonths).toDouble())) - 1) / periodRateOfInterest
        return (amount * mid * (1 + periodRateOfInterest)).toLong()
    }

    /*
    * Lump-sum Calculators work on the principle of future value.
    * The lump-sum calculator tells you the future value of your
    * investment at a certain rate of interest. You must use the
    * mathematical formula:
    * FV = PV(1+r)^n
    * FV = Future Value
    * PV = Present Value
    * r = Rate of interest
    * n = Number of years
    * For example, you have invested a lump sum amount of
    * Rs 1,00,000 in a mutual fund scheme for 20 years.
    * You have the expected rate of return of 10% on the investment.
    * You may calculate the future value of the investment as:
    * FV = 1,00,000(1+0.1)^20 FV = Rs 6,72,750.
    * You have invested Rs 1,00,000 which has grown to Rs 6,72,750.
    * The wealth gain is Rs 6,72,750 – Rs 1,00,000 = Rs 5,72,750.
    * */
    fun calculateLumpSum(
        amount: Long,
        duration: Int,
        interestRate: Float
    ): Long {
        return (amount * ((1 + (interestRate / 100)).pow(duration))).toLong()
    }


    /*
    * The formula for calculating expected interest and the maturity value is given below:
    A = P [({(1+i) ^n}-1)/i]
    Where,
    A is the maturity amount
    P is the principal amount invested in the PPF account
    I is the expected interest rate of return on PPF scheme
    N is the tenure for which is the amount is invested in PPF scheme
    * */
    fun calculatePPF(
        amount: Long,
        duration: Int,
        interestRate: Float
    ): Long {
        return ((amount * ((((1 + (interestRate / 100)).pow(duration)) - 1) / (interestRate / 100))) * (1 + (interestRate / 100))).toLong()
    }

    /** A = P (1+r/n) ^ (n * t)
    A = Maturity Amount
    P = Principal amount invested
    r = Rate of Interest (in decimals)
    n = number of compounding in a year
    t = number of years */
    fun calculateFD(
        amount: Long,
        duration: Int,
        interestRate: Float,
        compoundInYear: Int
    ): Long {
        val part1: Float = interestRate / (compoundInYear * 100)
        val end: Float = (1 + part1).pow(compoundInYear * (duration / 12))
        return (amount * end).toLong()
    }

    /** A = P (1+r/n) ^ (n * t)
    A = Maturity Amount
    P = Principal amount invested
    r = Rate of Interest (in decimals)
    n = number of compounding in a year
    t = number of years */
    fun calculateRD(
        amount: Long,
        duration: Int,
        interestRate: Float,
        compoundInYear: Int
    ): Long {
        var finalAmount: Double = 0.0
        for (i in 1..duration) {
            val part1: Float = interestRate / (compoundInYear * 100)
            val start = (1 + part1).toDouble()
            val power: Double = (compoundInYear * (i.toFloat() / 12)).toDouble()
            val end: Double = start.pow(power)
            finalAmount += amount * end
        }
        return finalAmount.toLong()
    }


    /*
    * Formula for EMI Calculation is -
    P x R x (1+R)^N / [(1+R)^N-1] where-
    P = Principal loan amount
    N = Loan tenure in months
    R = Monthly interest rate
    The rate of interest (R) on your loan is calculated per month.
    R = Annual Rate of interest/12/100
    If rate of interest is 7.2% p.a. then r = 7.2/12/100 = 0.006
    * */
    fun calculateHomeLoan(
        amount: Long,
        duration: Int,
        interestRate: Float
    ): Double {
        val r = interestRate / (12 * 100)
        //return monthly EMI
        return (amount * r * (((1 + r).pow(duration * 12)) / ((1 + r).pow(duration * 12) - 1))).toDouble()
    }


    fun getHomeCalculators(): ArrayList<Calculator> {
        val calculators: ArrayList<Calculator> = ArrayList()
        calculators.add(
            Calculator(
                "SIP Calculator",
                CalculatorType.SIP.name,
                "Calculate Systematic Investment Plan"
            )
        )
        calculators.add(
            Calculator(
                "Lumpsum Calculator",
                CalculatorType.LUMPSUM.name,
                "Calculate Lumpsum Investment"
            )
        )
        calculators.add(
            Calculator(
                "NSC Calculator",
                CalculatorType.NSC.name,
                "Calculate National Savings Certificates"
            )
        )
        calculators.add(
            Calculator(
                "PPF Calculator",
                CalculatorType.PPF.name,
                "Calculate Public Provident Fund"
            )
        )
        calculators.add(
            Calculator(
                "FD Calculator",
                CalculatorType.FD.name,
                "Calculate Fixed Deposit"
            )
        )
        calculators.add(Calculator("SEE_ALL", CalculatorType.SEEALL.name, ""))
        return calculators
    }

    fun getAllCalculators(): ArrayList<Calculator> {
        val calculators: ArrayList<Calculator> = ArrayList()
        calculators.add(
            Calculator(
                "SIP Calculator",
                CalculatorType.SIP.name,
                "Calculate Systematic Investment Plan"
            )
        )
        calculators.add(
            Calculator(
                "Lumpsum Calculator",
                CalculatorType.LUMPSUM.name,
                "Calculate Lumpsum Investment"
            )
        )
        calculators.add(
            Calculator(
                "NSC Calculator",
                CalculatorType.NSC.name,
                "Calculate National Savings Certificates"
            )
        )
        calculators.add(
            Calculator(
                "PPF Calculator",
                CalculatorType.PPF.name,
                "Calculate Public Provident Fund"
            )
        )
        calculators.add(
            Calculator(
                "FD Calculator",
                CalculatorType.FD.name,
                "Calculate Fixed Deposit"
            )
        )
        calculators.add(
            Calculator(
                "RD Calculator",
                CalculatorType.RD.name,
                "Calculate Recurring Deposit"
            )
        )
        calculators.add(
            Calculator(
                "EMI Calculator",
                CalculatorType.EMI.name,
                "Calculate Equated Monthly Instalment"
            )
        )
        calculators.add(
            Calculator(
                "Home Loan Calculator",
                CalculatorType.HOME_LOAN.name,
                "Calculate Home Loan Interest"
            )
        )
        calculators.add(
            Calculator(
                "Car Loan Calculator",
                CalculatorType.CAR_LOAN.name,
                "Calculate Car Loan Interest"
            )
        )
        calculators.add(
            Calculator(
                "Personal Loan Calculator",
                CalculatorType.PERSONAL_LOAN.name,
                "Calculate Personal Loan Interest"
            )
        )
        return calculators
    }
}