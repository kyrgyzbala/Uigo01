package kg.kyrgyzcoder.kassa01.ui.items.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelCategory
import kg.kyrgyzcoder.kassa01.databinding.RowCategoryBinding

class CategoriesRecyclerViewAdapter(
    private val listener: CategoryClickListener
) :
    ListAdapter<ModelCategory, CategoriesRecyclerViewAdapter.ViewHolderCat>(DIFF) {

    fun getItemAtPos(position: Int): ModelCategory {
        return getItem(position)
    }

    private var _binding: RowCategoryBinding? = null

    inner class ViewHolderCat(private val binding: RowCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItemAtPos(position)

            binding.categoryNameTextView.text = current.name

            binding.root.setOnClickListener {
                listener.onCategoryClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat {
        _binding = RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCat(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderCat, position: Int) {
        holder.onBind(position)
    }

    interface CategoryClickListener {
        fun onCategoryClick(position: Int)
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