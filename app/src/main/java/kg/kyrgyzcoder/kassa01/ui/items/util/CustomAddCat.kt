package kg.kyrgyzcoder.kassa01.ui.items.util

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomAddCategoryBinding
import kg.kyrgyzcoder.kassa01.util.hideKeyboard


class CustomAddCat(
    private val listener: CustomAddCatListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomAddCategoryBinding? = null
    private val binding: CustomAddCategoryBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomAddCategoryBinding.inflate(inflater, container, false)

        binding.catNameEditText.requestFocus()
        val imm: InputMethodManager? =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        binding.topButtonDialog.setOnClickListener {
            hideKeyboard()
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            hideKeyboard()
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            if (binding.catNameEditText.text.toString().isNotEmpty()) {
                hideKeyboard()
                listener.createNewFolder(binding.catNameEditText.text.toString())
                dismiss()
            } else {
                binding.catNameEditText.error = getString(R.string.requiredField)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }

    override fun onDismiss(dialog: DialogInterface) {
        hideKeyboard()
        super.onDismiss(dialog)
    }

    interface CustomAddCatListener {
        fun createNewFolder(name: String)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

}