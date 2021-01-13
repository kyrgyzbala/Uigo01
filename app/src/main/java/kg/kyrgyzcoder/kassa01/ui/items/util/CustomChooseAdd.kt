package kg.kyrgyzcoder.kassa01.ui.items.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomChooseAddBinding

class CustomChooseAdd(
    private val listener: CustomChooseAddListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomChooseAddBinding? = null
    private val binding: CustomChooseAddBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomChooseAddBinding.inflate(inflater, container, false)

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.scanButton.setOnClickListener {
            listener.addWithScan()
            dismiss()
        }

        binding.byHandButton.setOnClickListener {
            listener.addManually()
            dismiss()
        }

        return binding.root
    }

    interface CustomChooseAddListener {
        fun addWithScan()
        fun addManually()
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}