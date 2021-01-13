package kg.kyrgyzcoder.kassa01.ui.orders.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.kyrgyzcoder.kassa01.data.model.ModelOrderFake
import kg.kyrgyzcoder.kassa01.databinding.ActivityOrderDetailBinding
import kg.kyrgyzcoder.kassa01.ui.reports.util.ItemIncomeRecyclerViewAdapter

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding

    private lateinit var adapter: ItemIncomeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val order: ModelOrderFake? = intent.getSerializableExtra("EXTRA_ORDER") as ModelOrderFake
        if (order != null) {
            binding.clientCommentTextView.text = order.comment
            binding.textViewOrderDate.text = order.date

            adapter = ItemIncomeRecyclerViewAdapter()
            binding.recyclerViewItems.adapter = adapter
            adapter.submitList(order.detail)
        }

    }
}