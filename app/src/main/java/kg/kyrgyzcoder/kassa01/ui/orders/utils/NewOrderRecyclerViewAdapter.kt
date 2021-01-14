package kg.kyrgyzcoder.kassa01.ui.orders.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelFbOrder
import kg.kyrgyzcoder.kassa01.databinding.RowOrderBinding
import kg.kyrgyzcoder.kassa01.util.getDateToday
import java.text.SimpleDateFormat
import java.util.*

class NewOrderRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ModelFbOrder>,
    private val listener: OrderClickListener,
) :
    FirestoreRecyclerAdapter<ModelFbOrder, NewOrderRecyclerViewAdapter.ChatViewHolder>(options) {

    private var _binding: RowOrderBinding? = null

    inner class ChatViewHolder(private val binding: RowOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(current: ModelFbOrder) {

            loadLogo(current.clientId)

            binding.dateTextView.text = getDateToShow(current.dateOrder.toDate())
            binding.userName.text = if (current.clientName.isNullOrEmpty())
                binding.root.context.getString(R.string.notRegisteredUser)
            else
                current.clientName

            val text = "Количество: ${current.totalCount}"
            val text2 = "${current.totalCost} с."

            binding.itemCount.text = text
            binding.itemTotal.text = text2

            val type = if (current.orderType == 1)
                binding.root.context.getString(R.string.forDelivery)
            else
                binding.root.context.getString(R.string.forPickup)
            binding.textViewStatus.text = type

            binding.root.setOnClickListener {
                listener.onOrderClick(adapterPosition, current, type)
            }

        }

        private fun loadLogo(id: Int) {
            FirebaseFirestore.getInstance().collection("users").document("$id")
                .get().addOnSuccessListener {
                    val logo = it.getString("userLogo")
                    if (!logo.isNullOrEmpty())
                        Glide.with(binding.root).load(logo)
                            .error(
                                ContextCompat.getDrawable(
                                    binding.root.context,
                                    R.drawable.def_ava
                                )
                            ).into(binding.logoView)
                    else
                        Log.d("NURIKO", "loadLogo: logo null")
                }
        }

        private fun getDateToShow(date: Date): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT)
            val dateTimeStr = sdf.format(date)
            val timeStr = dateTimeStr.takeLast(5)
            val dateStr = getDateToday(itemView.context, dateTimeStr.take(10), 1)
            return "$dateStr $timeStr"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        _binding = RowOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(_binding!!)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int, model: ModelFbOrder) {
        holder.onBind(model)
    }

    interface OrderClickListener {
        fun onOrderClick(position: Int, model: ModelFbOrder, type: String)
    }

}