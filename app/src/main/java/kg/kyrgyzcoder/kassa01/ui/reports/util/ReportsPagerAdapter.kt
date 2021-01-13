package kg.kyrgyzcoder.kassa01.ui.reports.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.kyrgyzcoder.kassa01.ui.reports.InReportsFragment
import kg.kyrgyzcoder.kassa01.ui.reports.SalesReportFragment

class ReportsPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            SalesReportFragment()
        else
            InReportsFragment()
    }


}