package kg.kyrgyzcoder.kassa01.ui.more.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomCloseDayBinding

class CustomCloseDay(
    private val listener: CustomDayCloseListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomCloseDayBinding? = null
    private val binding: CustomCloseDayBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomCloseDayBinding.inflate(inflater, container, false)

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.quitButton.setOnClickListener {
            listener.closeDayConfirmed()
            dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface CustomDayCloseListener {
        fun closeDayConfirmed()
    }

}