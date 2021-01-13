package kg.kyrgyzcoder.kassa01.ui.orders.utils

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.kyrgyzcoder.kassa01.data.model.ModelOrderFake
import kg.kyrgyzcoder.kassa01.databinding.RowOrdersBinding

class OrdersRecyclerViewAdapter(private val listener: OrderClickListener) :
    ListAdapter<ModelOrderFake, OrdersRecyclerViewAdapter.ViewHolderOrders>(DIFF) {

    private var _binding: RowOrdersBinding? = null

    fun getItemAt(position: Int): ModelOrderFake {
        return getItem(position)
    }

    inner class ViewHolderOrders(private val binding: RowOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItem(position)
            binding.statusTextView.text = current.status
            binding.textViewOrderDate.text = current.date
            val on = "Заказ № ${current.orderNumber}"
            binding.textViewOrderNumber.text = on

            when (current.status) {
                "Новый" -> {
                    binding.statusTextView.setTextColor(Color.parseColor("#06710B"))
                }
                "Упокуется" -> {
                    binding.statusTextView.setTextColor(Color.parseColor("#03A9F4"))
                }
                "Готово" -> {
                    binding.statusTextView.setTextColor(Color.parseColor("#7C43E1"))
                }
                else -> {
                    binding.statusTextView.setTextColor(Color.parseColor("#290567"))
                }
            }

            binding.root.setOnClickListener {
                listener.onOrderClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOrders {
        _binding = RowOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderOrders(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderOrders, position: Int) {
        holder.onBind(position)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelOrderFake>() {
            override fun areItemsTheSame(
                oldItem: ModelOrderFake,
                newItem: ModelOrderFake
            ): Boolean {
                return oldItem.date == newItem.date && oldItem.comment == newItem.comment
            }

            override fun areContentsTheSame(
                oldItem: ModelOrderFake,
                newItem: ModelOrderFake
            ): Boolean {
                return oldItem.date == newItem.date &&
                        oldItem.comment == newItem.comment &&
                        oldItem.orderNumber == newItem.orderNumber
            }

        }
    }

    interface OrderClickListener {
        fun onOrderClick(position: Int)
    }
}