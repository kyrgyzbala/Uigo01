package kg.kyrgyzcoder.kassa01.ui.items.util

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomAddItemNameBinding

class CustomAddItemName(
    private val itemCode: String,
    private val listener: AddItemNameClickListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomAddItemNameBinding? = null
    private val binding: CustomAddItemNameBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomAddItemNameBinding.inflate(inflater, container, false)

        binding.topButtonDialog.setOnClickListener {
            listener.cancelled()
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            if (binding.itemCostEditText.text.toString().isNotEmpty()) {
                if (binding.itemCountEditText.toString().isNotEmpty()) {
                    listener.confirmAddItem(
                        itemCode,
                        binding.itemCostEditText.text.toString().toFloat(),
                        binding.itemCountEditText.text.toString().toInt()
                    )
                    dismiss()
                } else {
                    binding.itemCountEditText.error = getString(R.string.requiredField)
                }
            } else {
                binding.itemCostEditText.error = getString(R.string.requiredField)
            }
        }


        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        listener.cancelled()
        super.onDismiss(dialog)
    }

    interface AddItemNameClickListener {
        fun confirmAddItem(itemCode: String, itemCost: Float, itemCount: Int)
        fun cancelled()
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}