package kg.kyrgyzcoder.kassa01.ui.orders.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelItemFb
import kg.kyrgyzcoder.kassa01.databinding.RowOrderItemsBinding

class RecyclerViewOrderItems(
    options: FirestoreRecyclerOptions<ModelItemFb>,
    private val listener: ItemOderClickListener,
) : FirestoreRecyclerAdapter<ModelItemFb, RecyclerViewOrderItems.ViewHolderI>(options) {

    private var _binding: RowOrderItemsBinding? = null

    inner class ViewHolderI(private val binding: RowOrderItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(current: ModelItemFb) {
            binding.textViewItemName.text = current.name
            val count = current.count.toString() + " x ${current.cost} c"
            val cost = current.total.toString() + " c."
            binding.costTextView.text = cost
            binding.itemCount.text = count

            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition, current.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderI {
        _binding = RowOrderItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderI(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderI, position: Int, model: ModelItemFb) {
        holder.onBind(model)
    }

    interface ItemOderClickListener {
        fun onItemClick(position: Int, itemId: Int)
    }

}