package kg.kyrgyzcoder.kassa01.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.kyrgyzcoder.kassa01.ui.items.ItemsMainFragment
import kg.kyrgyzcoder.kassa01.ui.more.MoreMainFragment
import kg.kyrgyzcoder.kassa01.ui.orders.OrdersFragment
import kg.kyrgyzcoder.kassa01.ui.reports.ReportsMainFragment
import kg.kyrgyzcoder.kassa01.ui.sell.SellMainFragment

class MainPagerAdapter(
    fm: FragmentActivity
) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ReportsMainFragment()
            1 -> OrdersFragment()
            2 -> ItemsMainFragment()
            3 -> SellMainFragment()
            else -> MoreMainFragment()
        }
    }
}