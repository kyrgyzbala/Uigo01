package kg.kyrgyzcoder.kassa01.ui.orders

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.kyrgyzcoder.kassa01.data.model.ModelItem
import kg.kyrgyzcoder.kassa01.data.model.ModelOrderFake
import kg.kyrgyzcoder.kassa01.databinding.FragmentOrdersBinding
import kg.kyrgyzcoder.kassa01.ui.orders.detail.OrderDetailActivity
import kg.kyrgyzcoder.kassa01.ui.orders.utils.OrdersRecyclerViewAdapter

class OrdersFragment : Fragment(), OrdersRecyclerViewAdapter.OrderClickListener {

    private var _binding: FragmentOrdersBinding? = null
    private val binding: FragmentOrdersBinding get() = _binding!!

    private lateinit var adapter: OrdersRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fakeOrders = listOf<ModelOrderFake>(
            generateFakeOrder(
                1237L,
                "Сегодня 13:41 ПП",
                "Новый",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1236L,
                "Сегодня 13:22 ПП",
                "Упокуется",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1235L,
                "Сегодня 13:01 ПП",
                "Готово",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1234L,
                "Сегодня 12:12 ПП",
                "Завершено",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1237L,
                "Сегодня 13:41 ПП",
                "Новый",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1236L,
                "Сегодня 13:22 ПП",
                "Упокуется",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1235L,
                "Сегодня 13:01 ПП",
                "Готово",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1234L,
                "Сегодня 12:12 ПП",
                "Завершено",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1237L,
                "Сегодня 13:41 ПП",
                "Новый",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1236L,
                "Сегодня 13:22 ПП",
                "Упокуется",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1235L,
                "Сегодня 13:01 ПП",
                "Готово",
                "Сураныч аккуратно упокуйте"
            ),
            generateFakeOrder(
                1234L,
                "Сегодня 12:12 ПП",
                "Завершено",
                "Сураныч аккуратно упокуйте1"
            )
        )

        adapter = OrdersRecyclerViewAdapter(this)
        binding.recyclerViewOrders.setHasFixedSize(true)
        binding.recyclerViewOrders.adapter = adapter
        adapter.submitList(fakeOrders)
    }

    private fun generateFakeOrder(
        num: Long,
        date: String,
        status: String,
        comment: String
    ): ModelOrderFake {
        val items = listOf<ModelItem>(generateTestItem("Albeni mini", 12, 15.0),
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
            generateTestItem("Чипсы Lays Large", 4, 20.0)
        )
        return ModelOrderFake(num, date, status, comment, items)
    }

    private fun generateTestItem(name: String, q: Int, cost: Double): ModelItem {
        return ModelItem(
            name, quantity = q, cost = cost, total = (q * cost)
        )
    }

    override fun onOrderClick(position: Int) {
        val current = adapter.getItemAt(position)
        val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
        intent.putExtra("EXTRA_ORDER", current)
        startActivity(intent)
    }


}