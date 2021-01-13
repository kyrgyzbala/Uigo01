package kg.kyrgyzcoder.kassa01.ui.sell.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomDeleteBinding

class CustomDelete(
    private val text: String,
    private val cost: Float,
    private val itemId: Int,
    private val listener: CustomDeleteListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomDeleteBinding? = null
    private val binding: CustomDeleteBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomDeleteBinding.inflate(inflater, container, false)

        binding.textView.text = text

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.deleteButton.setOnClickListener {
            listener.onDeleteConfirmed(cost, itemId)
            dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface CustomDeleteListener {
        fun onDeleteConfirmed(cost: Float, itemId: Int)
    }
}