package kg.kyrgyzcoder.kassa01.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelRegister
import kg.kyrgyzcoder.kassa01.databinding.ActivityRegisterBinding
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginViewModel
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.main.MainActivity
import kg.kyrgyzcoder.kassa01.util.hide
import kg.kyrgyzcoder.kassa01.util.hideKeyboard
import kg.kyrgyzcoder.kassa01.util.show
import kg.kyrgyzcoder.kassa01.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity(), KodeinAware, LoginListener {

    override val kodein: Kodein by closestKodein()
    private val loginViewModelFactory: LoginViewModelFactory by instance()

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        loginViewModel =
            ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
        loginViewModel.setListener(this)

        addEditTextListeners()

        binding.arrBackRegister.setOnClickListener {
            onBackPressed()
        }

        binding.buttonLogin.setOnClickListener {
            onBackPressed()
        }

        binding.buttonRegister.setOnClickListener {
            if (checkFields() && checkPasswordMatch()) {
                hideKeyboard()
                binding.progressBar.show()
                val modelRegister = ModelRegister(
                    binding.phoneEditText.text.toString(),
                    binding.passwordEditTextRegister.text.toString(),
                    binding.userNameEditText.text.toString(),
                    2, binding.storeNameEditText.text.toString(),
                    binding.addressEditText.text.toString(),
                    ""
                )
                loginViewModel.registerNewUser(modelRegister)
            }
        }

    }

    override fun loginSuccess() {
        binding.progressBar.hide()
        toast(getString(R.string.welcome))
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(intent)
    }

    override fun loginFail(code: Int?) {
        binding.progressBar.hide()
        toast("Произошла ошибка код: $code, Пожалуйста повторите заново!")
    }

    override fun userExists() {
        binding.progressBar.hide()
        binding.userNameError.text = getString(R.string.userExists)
        binding.userNameError.visibility = View.VISIBLE
    }

    private fun checkPasswordMatch(): Boolean {
        val pwd1 = binding.passwordEditTextRegister.text.toString()
        val pwd2 = binding.passwordEditTextRegister2.text.toString()

        return if (pwd1 == pwd2)
            true
        else {
            binding.password2Error.visibility = View.VISIBLE
            binding.password2Error.text = getString(R.string.pwdMustMatch)
            false
        }
    }

    private fun addEditTextListeners() {
        binding.userNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.userNameError.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.storeNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.storeNameError.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.addressEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.addressError.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.passwordEditTextRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.pwdError.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.passwordEditTextRegister2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.password2Error.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    private fun checkFields(): Boolean {
        var ret = true

        val userName = binding.userNameEditText.text.toString()

        if (userName.isEmpty()) {
            ret = false
            binding.userNameError.visibility = View.VISIBLE
            binding.userNameError.text = getString(R.string.requiredField)
        }

        if (userName.length < 6) {
            ret = false
            binding.userNameError.visibility = View.VISIBLE
            binding.userNameError.text = getString(R.string.mustContainSix)
        }

        if (binding.storeNameEditText.text.toString().isEmpty()) {
            ret = false
            binding.storeNameError.visibility = View.VISIBLE
        }

        if (binding.addressEditText.text.toString().isEmpty()) {
            ret = false
            binding.addressError.visibility = View.VISIBLE
        }

        if (binding.passwordEditTextRegister.text.toString()
                .isEmpty() || binding.passwordEditTextRegister.text.toString().length < 6
        ) {
            ret = false
            binding.pwdError.visibility = View.VISIBLE
            binding.pwdError.text = getString(R.string.mustContainSix)
        }


        if (binding.passwordEditTextRegister2.text.toString().isEmpty()) {
            ret = false
            binding.password2Error.visibility = View.VISIBLE
        }

        return ret
    }


}