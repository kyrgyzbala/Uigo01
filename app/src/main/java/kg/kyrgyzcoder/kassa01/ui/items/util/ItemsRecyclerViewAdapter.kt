package kg.kyrgyzcoder.kassa01.ui.items.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelActive
import kg.kyrgyzcoder.kassa01.databinding.RowItemsBinding

class ItemsRecyclerViewAdapter(
    private val listener: ItemsClickListener
) : ListAdapter<ModelActive, ItemsRecyclerViewAdapter.ViewHolder>(DIFF) {

    private var _binding: RowItemsBinding? = null

    fun getItemAtPos(position: Int): ModelActive {
        return getItem(position)
    }

    inner class ViewHolder(private val binding: RowItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItemAtPos(position)

            binding.textViewItemName.text = current.itemglobal.name
            binding.textViewItemCost.text = current.cost.toString()

            binding.root.setOnClickListener {
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = RowItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    interface ItemsClickListener {
        fun onItemClick(position: Int)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelActive>() {
            override fun areItemsTheSame(oldItem: ModelActive, newItem: ModelActive): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ModelActive, newItem: ModelActive): Boolean {
                return oldItem.cost == newItem.cost &&
                        oldItem.id == newItem.id
            }

        }
    }

}