package kg.kyrgyzcoder.kassa01.ui.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelActive
import kg.kyrgyzcoder.kassa01.databinding.RowCheckoutBinding
import kg.kyrgyzcoder.kassa01.databinding.RowScanResultBinding
import kg.kyrgyzcoder.kassa01.util.LIMIT_ITEMS

class SellRecyclerViewAdapter(
    private val listener: SellCheckoutClickListener
) : ListAdapter<ModelActive, SellRecyclerViewAdapter.ViewHolderSell>(DIFF) {

    private var _binding: RowCheckoutBinding? = null

    fun getItemAtPos(position: Int): ModelActive {
        return getItem(position)
    }

    inner class ViewHolderSell(val binding: RowCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItem(position)
            updateUI(current)
            binding.itemNameTextView.text = current.itemglobal.name
            binding.plusButton.setOnClickListener {
                var count2 = binding.countTextView.text.toString().toInt()
                if (count2 < LIMIT_ITEMS) {
                    count2++
                    binding.countTextView.text = count2.toString()
                    updateUI(current)
                    listener.addQuantity(current.id, current.cost)
                }
            }

            binding.minusButton.setOnClickListener {
                var count2 = binding.countTextView.text.toString().toInt()
                if (count2 > 1) {
                    count2--
                    binding.countTextView.text = count2.toString()
                    updateUI(current)
                    listener.subtractQuantity(current.id, false, current.cost)
                } else {
                    listener.subtractQuantity(current.id, true, current.cost)
                }
            }
        }

        private fun updateUI(current: ModelActive) {
            val count = binding.countTextView.text.toString().toInt()
            val cost = current.cost.toString() + " с. x $count"
            binding.itemCost.text = cost
            val t = count.toFloat() * current.cost
            val totalCost = "$t c."
            binding.itemTotalCost.text = totalCost
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSell {
        _binding = RowCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderSell(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderSell, position: Int) {
        holder.onBind(position)
    }


    interface SellCheckoutClickListener {
        fun addQuantity(itemId: Int, totalCost: Float)
        fun subtractQuantity(itemId: Int, isDeleting: Boolean, totalCost: Float)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelActive>() {
            override fun areItemsTheSame(oldItem: ModelActive, newItem: ModelActive): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ModelActive, newItem: ModelActive): Boolean {
                return oldItem.cost == newItem.cost
            }

        }
    }

}