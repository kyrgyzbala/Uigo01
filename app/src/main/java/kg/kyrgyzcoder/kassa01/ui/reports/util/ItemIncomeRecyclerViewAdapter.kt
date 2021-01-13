package kg.kyrgyzcoder.kassa01.ui.reports.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.kyrgyzcoder.kassa01.data.model.ModelItem
import kg.kyrgyzcoder.kassa01.databinding.RowIncomeBinding

class ItemIncomeRecyclerViewAdapter : ListAdapter<ModelItem, ItemIncomeRecyclerViewAdapter.ViewHolder>(DIFF) {

    private var _binding: RowIncomeBinding? = null

    fun getItemAt(position: Int): ModelItem {
        return getItem(position)
    }

    inner class ViewHolder(private val binding: RowIncomeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val item = getItemAt(position)

            binding.textViewItemName.text = item.itemName
            binding.costTextView.text = item.cost.toString() + " s."
            binding.quantityTextView.text = item.quantity.toString()
            binding.totalTextView.text = item.total.toString() + " s."
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = RowIncomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    companion object{
        private val DIFF = object : DiffUtil.ItemCallback<ModelItem>() {
            override fun areItemsTheSame(oldItem: ModelItem, newItem: ModelItem): Boolean {
                return oldItem.itemName == newItem.itemName
            }

            override fun areContentsTheSame(oldItem: ModelItem, newItem: ModelItem): Boolean {
                return oldItem.itemName == newItem.itemName && oldItem.cost == newItem.cost
            }

        }
    }

}