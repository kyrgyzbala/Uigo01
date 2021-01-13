package kg.kyrgyzcoder.kassa01.ui.sell.util

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomTypeBarcodeBinding

class CustomSearchBarcode(
    private val listener: CustomSearchBarcodeListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomTypeBarcodeBinding? = null
    private val binding: CustomTypeBarcodeBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomTypeBarcodeBinding.inflate(inflater, container, false)

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            if (binding.barcodeEditText.text.toString().isNotEmpty()) {
                listener.onSearchBarcode(binding.barcodeEditText.text.toString())
                dismiss()
            } else {
                binding.barcodeEditText.error = getString(R.string.requiredField)
            }
        }

        binding.barcodeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.barcodeEditText.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface CustomSearchBarcodeListener {
        fun onSearchBarcode(barcode: String)
    }

}