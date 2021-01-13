package kg.kyrgyzcoder.kassa01.ui.sell.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelCategory
import kg.kyrgyzcoder.kassa01.databinding.RowCatFilterBinding

class Cat2RecyclerViewAdapter(
    private val listener: Cat2ClickListener
) : ListAdapter<ModelCategory, Cat2RecyclerViewAdapter.ViewHolderCat2>(DIFF) {

    private var _binding: RowCatFilterBinding? = null

    fun getItemAtPos(position: Int): ModelCategory {
        return getItem(position)
    }

    inner class ViewHolderCat2(private val binding: RowCatFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItemAtPos(position)
            binding.textViewCatName.text = current.name

            binding.root.setOnClickListener {
                listener.onCategoryClick(current.id, current.name)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat2 {
        _binding = RowCatFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCat2(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderCat2, position: Int) {
        holder.onBind(position)
    }

    interface Cat2ClickListener {
        fun onCategoryClick(catId: Int, catName: String)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelCategory>() {
            override fun areItemsTheSame(oldItem: ModelCategory, newItem: ModelCategory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ModelCategory,
                newItem: ModelCategory
            ): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.storeid == newItem.storeid
            }

        }
    }

}