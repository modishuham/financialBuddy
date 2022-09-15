package com.shubham.financialbuddy.ui.investment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shubham.financialbuddy.base.BaseFragment
import com.shubham.financialbuddy.databinding.FragmentInvestmentBinding
import com.shubham.financialbuddy.model.RemoteConfigData

class InvestmentFragment : BaseFragment() {

    private lateinit var mBinding: FragmentInvestmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentInvestmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvInvestmentPlans.adapter = InvestmentPlanAdapter(getInvestmentPlans())

    }

    private fun getInvestmentPlans(): ArrayList<InvestmentPlan> {
        val list = ArrayList<InvestmentPlan>()

        val title1 = InvestmentPlan(
            "TITLE", "Investment Plans For Long-Term",
            "Long-term goals are the ones that you would want to achieve in the next 7-10 years. Therefore, when you look to pick the best investment plans for the long term, you may go for the options which are volatile but have the potential to deliver high returns over the long term.",
            "",
            "",
            false
        )
        list.add(title1)

        val plan1 = InvestmentPlan(
            "Plan", "Direct Equity (Stock Market)",
            "<p>One of the best ways to create wealth for your long-term goals is to invest in equities. There are many examples of stocks that have multiplied investors’ wealth over time.<br><br>" +
                    "Stock market can give you handsome returns but this is also a high risky investment. We suggested that if you don't have much knowledge in stock market then please ignore investing in it.<br><br>" +
                    "To start investing in stock market we required a DEMAT account. There are many stock brokers are available where you can open your DEMAT account easily. EX. Zerodha, Upstox, Groww</p>",
            "High",
            "15-20%",
            false
        )
        list.add(plan1)

        val plan2 = InvestmentPlan(
            "Plan", "Mutual Funds",
            "<p>In case you don't have knowledge about stock markets or you don't want to invest your time to analysis the stocks then mutual funds will be a great option for you. In mutual funds you can invest your lumpsum amount or do a monthly SIP (Systematic Investment Plan). Mutual funds reduce the risk and take professional help and diversify your investments across multiple stocks<br><br>" +
                    "Equity Mutual Funds primarily invest in stocks. But they don’t concentrate your money on just 1 or 2 stocks. These funds diversify your investments across multiple stocks. More importantly, professional fund managers run these funds. So they invest your money only after adequate research. As a result, it increases your chances of earning good returns over the long term.<br><br>" +
                    "There are many types of mutual funds available. You can select the right fund as per your risk capacity. Some most popular funds are following - <br><br>" +
                    "<b><h4>Equity Funds</h4></b> For long term (4 years or more) -<br><br>" +
                    "Equity mutual funds invest your money in stock market. Investment in these funds might be risky because of market volatility. Equity mutual funds are also divided in many types -<br><br>" +
                    "<b><h5>Large cap</h5></b> - Invest in top 100 stocks.<br><br>" +
                    "<b><h5>Mid cap</h5></b> - Invest next 150 stocks.<br><br>" +
                    "<b><h5>Small cap</h5></b> - Invest outside top 250 stocks.<br><br>" +
                    "<b><h5>Multi cap</h5></b> - Invests in stocks across market cap.<br><br>" +
                    "<b><h5>ELSS (Equity Linked Saving Scheme)</h5></b> - Invests in stocks across market cap. Get Tax benefits under 80c.<br><br>" +
                    "<b><h4>Dept Funds</h4></b> For short to medium term -<br><br>" +
                    "Low risk in comparison to equity mutual funds. But returns are also low in comparison to equity funds. In dept funds your amount is mostly invested in government bonds and schemes where risk is low.",
            "High",
            "15-20%",
            false
        )
        list.add(plan2)

        val plan3 = InvestmentPlan(
            "Plan", "Gold",
            "<p>Gold has been a symbol of wealth since ancient times. And even now, it has not lost its shine as an investment option that can beat inflation.<br><br>" +
                    "Physical gold has been the traditional way to buy the yellow metal. But it comes with limitations like extra making or designing charges or storage expenses. To overcome these limitations, you can buy gold through Mutual Funds and ETFs.</p>",
            "Medium",
            "",
            false
        )
        list.add(plan3)

        val plan4 = InvestmentPlan(
            "Plan", "Real Estate",
            "<p>It is certainly one of the most popular investment options among Indians. Nevertheless, while property investments have delivered stunning returns in the past, it has their own set of risks and limitations. One of the major risks with real estate is that you may not sell it in a short period. And in a rush to sell the property, you may have to sell at a deep discount.<br><br>" +
                    "Further, even if the money you need is smaller than the property, you will have to sell the entire property to get the money.</p>",
            "Low",
            "6.6-7.6%",
            false
        )
        list.add(plan4)

        val plan5 = InvestmentPlan(
            "Plan", "Small Saving Schemes Like PPF",
            "<p>The government has introduced many small saving schemes for people who want to invest in highly safe investment options. These schemes offer assured returns to investors with little volatility. But you earn lower returns than market-linked products like NPS, Mutual Funds, or stocks.<br><br>" +
                    "That said, small saving schemes typically beat inflation and FDs by a decent margin. Examples of small saving schemes for the long term include investment options like Public Provident Fund (PPF), Senior Citizens Savings Scheme (SCSS), the Sukanya Samriddhi Scheme, and the Kisan Vikas Patra.<br><br>" +
                    "The following shows some of the small saving schemes suitable for long-term investment and the returns you can earn from them<br><br>" +
                    "<b><h4>Senior Citizen Savings Scheme (Interest rate " + RemoteConfigData.scssInterest + "%)</h4></b><br><br>" +
                    "<b><h4>Public Provident Fund Scheme (Interest rate " + RemoteConfigData.ppfInterest + "%)</h4></b><br><br>" +
                    "<b><h4>Kisan Vikas Patra (Interest rate " + RemoteConfigData.kvpInterest + "%)</h4></b><br><br>" +
                    "<b><h4>Sukanya Samriddhi Account Scheme (Interest rate " + RemoteConfigData.ssasInterest + "%)</h4></b></p>",
            "Low",
            "6.9-7.6%",
            false
        )
        list.add(plan5)

        val plan6 = InvestmentPlan(
            "Plan", "NPS",
            "<p>The National Pension System (NPS) is a long-term retirement-focused investment product. It is a mix of different assets like equities, government bonds, and corporate bonds. You can decide how much of your money can be invested in different asset classes based on your risk appetite.</p>",
            "Medium",
            "",
            false
        )
        list.add(plan6)

        val plan7 = InvestmentPlan(
            "Plan", "ULIPs",
            "<p>A Unit-Linked Insurance Plan (ULIP) combines life insurance and investment. A part of your premium is invested in asset classes like equity and bonds to generate wealth over the long term. Another part of your premium goes towards a life insurance cover.<br><br>" +
                    "In the past, ULIPs have been notorious for high charges. However, new ULIPs do not have that high charges. But they still come with a lockin of 5 years.</p>",
            "Low",
            "",
            false
        )
        list.add(plan7)

        val title2 = InvestmentPlan(
            "TITLE", "Investment Plans For Medium Term",
            "Medium-term goals are those goals that are 3-5 years away. For instance, medium-term goals can be saving for your wedding, downpayment of house, house renovation, etc.",
            "",
            "",
            false
        )
        list.add(title2)

        val plan8 = InvestmentPlan(
            "Plan", "National Savings Certificates (NSC)",
            "<p>National Savings Certificate or NSC is a post office savings product backed by the government of India. It works like a 5-year FD. So your deposits in NSC will mature in 5 years, and you will earn " + RemoteConfigData.nscInterest + "% annual interest. But the entire amount is payable only at maturity.<br><br>" +
                    "So, if you have a goal that is 5 years away, NSC is one of the safer investment options. But it comes with limitations like 5-year lock-in and subdued returns as compared to Debt Funds or Hybrid Funds.</p>",
            "Low",
            "6.8%",
            false
        )
        list.add(plan8)

        val plan9 = InvestmentPlan(
            "Plan", "Post Office Time Deposit",
            "<p>Like banks, post offices also offer FDs. Known as Post Office Time Deposit, these investment options allow you to deposit your money for short-medium time periods. The advantage of Post Office Time Deposits is that they offer better returns than banks. And that too without any additional risk because these schemes are backed by the government of India.<br><br>" +
                    "Here is the example that shows returns from Post Office Time Deposits for various time periods.<br><br>" +
                    "<b><h4>1 year (Interest rate 5.5%)</h4></b><br>" +
                    "<b><h4>2 years (Interest rate 5.5%)</h4></b><br>" +
                    "<b><h4>3 years (Interest rate 5.5%)</h4></b><br>" +
                    "<b><h4>5 years (Interest rate 6.7%)</h4></b></p>",
            "Low",
            "",
            false
        )
        list.add(plan9)

        val plan10 = InvestmentPlan(
            "Plan", "Bank Fixed Deposits (FDs)",
            "<p>This is one of India’s most popular investment options as they offer guaranteed returns. The way FDs work is quite simple. You deposit your money in the bank, which assures you a certain return on your principal investment at the end of the tenure.<br><br>" +
                    "While FDs are one of the safest investment options, they have some significant limitations. The post-tax returns from FDs barely beat inflation. It means if you are investing in FDs, you are essentially earning negative returns and eroding your wealth with time. Also, FDs levy a penalty if you withdraw your investments before their maturity. So the liquidity of FDs is a significant limitation as well.</p>",
            "Low",
            "4-6%",
            false
        )
        list.add(plan10)

        return list
    }
}