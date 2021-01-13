package kg.kyrgyzcoder.kassa01.ui.items.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomAddManualBinding

class CustomAddManually(
    private val listener: CustomAddManualListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomAddManualBinding? = null
    private val binding: CustomAddManualBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomAddManualBinding.inflate(inflater, container, false)

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            if (checkFields()) {
                listener.addNewItem(
                    binding.itemCodeEditText.text.toString(),
                    binding.itemCostEditText.text.toString().toFloat(),
                    binding.itemCountEditText.text.toString().toInt()
                )
                dismiss()
            }
        }

        return binding.root
    }

    private fun checkFields(): Boolean {
        var ret = true

        if (binding.itemCodeEditText.text.toString().isEmpty()) {
            ret = false
            binding.itemCodeEditText.error = getString(R.string.requiredField)
        }

        if (binding.itemCostEditText.text.toString().isEmpty()) {
            ret = false
            binding.itemCostEditText.error = getString(R.string.requiredField)
        }

        if (binding.itemCountEditText.text.toString().isEmpty()) {
            ret = false
            binding.itemCountEditText.error = getString(R.string.requiredField)
        }

        return ret
    }

    interface CustomAddManualListener {
        fun addNewItem(itemCode: String, itemCost: Float, itemCount: Int)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}