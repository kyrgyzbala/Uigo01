package kg.kyrgyzcoder.kassa01.ui.orders.utils

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelFbOrder
import kg.kyrgyzcoder.kassa01.databinding.CustomFillAddressBinding

class CustomAddressConfirm(
    private val address: String,
    private val phone: String,
    private val order: ModelFbOrder,
    private val listener: CustomAddressConfirmListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomFillAddressBinding? = null
    private val binding: CustomFillAddressBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CustomFillAddressBinding.inflate(inflater, container, false)

        binding.topButton.setOnClickListener {
            dismiss()
        }

        binding.addressEditText.setText(address)
        binding.phoneEditText.setText(phone)

        addListeners()

        binding.okButton.setOnClickListener {
            if (checkInputs()) {
                listener.onChangeClick(address, phone, order)
                dismiss()
            }
        }

        return binding.root
    }

    private fun addListeners() {
        binding.addressEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.addressEditText.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.phoneEditText.error = getString(R.string.requiredField)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun checkInputs(): Boolean {
        var ret = true

        if (binding.addressEditText.text.toString().isEmpty()) {
            ret = false
            binding.addressEditText.error = getString(R.string.requiredField)
        }

        if (binding.phoneEditText.text.toString().isEmpty()) {
            ret = false
            binding.phoneEditText.error = getString(R.string.requiredField)
        }

        return ret
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface CustomAddressConfirmListener {
        fun onChangeClick(address: String, phone: String, order: ModelFbOrder)
    }

}