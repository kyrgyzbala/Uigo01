package kg.kyrgyzcoder.kassa01.ui.orders.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomConfirmCallBinding

class CustomCallUser(
    private val phoneNumber: String?,
    private val listener: CustomCallUserListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomConfirmCallBinding? = null
    private val binding: CustomConfirmCallBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CustomConfirmCallBinding.inflate(inflater, container, false)

        binding.topButton.setOnClickListener {
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            listener.callUserConfirmed(phoneNumber)
            dismiss()
        }

        return binding.root
    }

    interface CustomCallUserListener {
        fun callUserConfirmed(phoneNumber: String?)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}