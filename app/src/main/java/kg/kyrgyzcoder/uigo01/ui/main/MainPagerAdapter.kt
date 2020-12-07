package kg.kyrgyzcoder.uigo01.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.kyrgyzcoder.uigo01.ui.items.ItemsMainFragment
import kg.kyrgyzcoder.uigo01.ui.more.MoreMainFragment
import kg.kyrgyzcoder.uigo01.ui.reports.ReportsMainFragment
import kg.kyrgyzcoder.uigo01.ui.sell.SellMainFragment

class MainPagerAdapter(
    fm: FragmentActivity
) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ReportsMainFragment()
            1 -> ItemsMainFragment()
            2 -> SellMainFragment()
            else -> MoreMainFragment()
        }
    }
}