package kg.kyrgyzcoder.kassa01.ui.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.kyrgyzcoder.kassa01.data.model.ModelItem
import kg.kyrgyzcoder.kassa01.databinding.FragmentSalesReportBinding
import kg.kyrgyzcoder.kassa01.ui.reports.util.ItemIncomeRecyclerViewAdapter
import kg.kyrgyzcoder.kassa01.util.hide


class SalesReportFragment : Fragment() {

    private var _binding: FragmentSalesReportBinding? = null
    private val binding: FragmentSalesReportBinding get() = _binding!!

    private lateinit var adapter: ItemIncomeRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSalesReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleTestData()

    }

    private fun handleTestData() {
        val items = listOf(
            generateTestItem("Albeni mini", 12, 15.0),
            generateTestItem("Вода Легенда 0.5 л", 4, 20.0),
            generateTestItem("Сендвич от Тойбос с кетчупом 450г", 4, 20.0),
            generateTestItem("Кока-Кола без сахара 2.5 л", 4, 20.0),
            generateTestItem("Семечки джин 250г", 4, 20.0),
            generateTestItem("Чипсы Lays Large", 4, 20.0),
            generateTestItem("Albeni mini", 12, 15.0),
            generateTestItem("Вода Легенда 0.5 л", 4, 20.0),
            generateTestItem("Сендвич от Тойбос с кетчупом 450г", 4, 20.0),
            generateTestItem("Кока-Кола без сахара 2.5 л", 4, 20.0),
            generateTestItem("Семечки джин 250г", 4, 20.0),
            generateTestItem("Чипсы Lays Large", 4, 20.0),
        )
        adapter = ItemIncomeRecyclerViewAdapter()
        binding.recyclerViewIncome.setHasFixedSize(true)
        binding.recyclerViewIncome.adapter = adapter
        adapter.submitList(items)

        binding.prBar2.hide()
        binding.prBarReports.hide()
    }


    private fun generateTestItem(name: String, q: Int, cost: Double): ModelItem {
        return ModelItem(
            name, quantity = q, cost = cost, total = (q * cost)
        )
    }

}