package kg.kyrgyzcoder.kassa01.ui.orders.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelFbOrder
import kg.kyrgyzcoder.kassa01.databinding.CustomConfirmOrderBinding

class CustomConfirmOrder(
    private val order: ModelFbOrder,
    private val listener: CustomConfirmOrderListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomConfirmOrderBinding? = null
    private val binding: CustomConfirmOrderBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CustomConfirmOrderBinding.inflate(inflater, container, false)

        binding.topButton.setOnClickListener {
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            listener.orderConfirmed(order)
            dismiss()
        }

        return binding.root
    }

    interface CustomConfirmOrderListener {
        fun orderConfirmed(order: ModelFbOrder)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}