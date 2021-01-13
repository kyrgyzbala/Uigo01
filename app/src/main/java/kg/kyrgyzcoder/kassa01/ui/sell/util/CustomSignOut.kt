package kg.kyrgyzcoder.kassa01.ui.sell.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.CustomSignOutBinding

class CustomSignOut (
    private val text: String,
    private val listener: CustomSignOutListener
) : BottomSheetDialogFragment() {

    private var _binding: CustomSignOutBinding? = null
    private val binding: CustomSignOutBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomSignOutBinding.inflate(inflater, container, false)

        binding.topButtonDialog.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.textView.text = text
        binding.quitButton.setOnClickListener {
            listener.onSignOutConfirm()
            dismiss()
        }

        return binding.root
    }


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface CustomSignOutListener {
        fun onSignOutConfirm ()
    }

}