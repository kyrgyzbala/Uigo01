package kg.kyrgyzcoder.kassa01.ui.orders.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelFbOrder
import kg.kyrgyzcoder.kassa01.databinding.CustomAskForDeliveryBinding

class CustomAskForDelivery(
    private val order: ModelFbOrder,
    private val listener: CustomAskForDeliveryListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomAskForDeliveryBinding? = null
    private val binding: CustomAskForDeliveryBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CustomAskForDeliveryBinding.inflate(inflater, container, false)

        binding.topButton.setOnClickListener {
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            listener.deliveryNotNeeded()
            dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            listener.callDeliveryConfirmed(order)
            dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface CustomAskForDeliveryListener {
        fun callDeliveryConfirmed(order: ModelFbOrder)
        fun deliveryNotNeeded()
    }

}