package kg.kyrgyzcoder.kassa01.ui.orders.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.kyrgyzcoder.kassa01.ui.orders.ConfirmedOrdersFragment
import kg.kyrgyzcoder.kassa01.ui.orders.NewOrdersFragment

class OrdersPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewOrdersFragment()
            else -> ConfirmedOrdersFragment()
        }
    }
}