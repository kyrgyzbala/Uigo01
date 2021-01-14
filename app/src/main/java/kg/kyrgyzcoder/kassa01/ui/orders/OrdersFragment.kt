package kg.kyrgyzcoder.kassa01.ui.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.FragmentOrdersBinding
import kg.kyrgyzcoder.kassa01.ui.orders.utils.OrdersPagerAdapter

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding: FragmentOrdersBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs = listOf(
            getString(R.string.newOrders),
            getString(R.string.confirmedOrders)
        )

        binding.tabsMain.tabMode = TabLayout.MODE_SCROLLABLE

        val adapter = OrdersPagerAdapter(requireActivity())

        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabsMain, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

    }


}