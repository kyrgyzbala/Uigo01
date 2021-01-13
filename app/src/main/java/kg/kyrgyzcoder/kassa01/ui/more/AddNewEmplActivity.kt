package kg.kyrgyzcoder.kassa01.ui.more

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCreateCashier
import kg.kyrgyzcoder.kassa01.databinding.ActivityAddNewEmplBinding
import kg.kyrgyzcoder.kassa01.ui.login.LoginCashierListener
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginCashierViewModel
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginCashierViewModelFactory
import kg.kyrgyzcoder.kassa01.util.EXTRA_ADD_TYPE
import kg.kyrgyzcoder.kassa01.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class AddNewEmplActivity : AppCompatActivity(), KodeinAware, LoginCashierListener {

    override val kodein: Kodein by closestKodein()
    private val loginCashierViewModelFactory: LoginCashierViewModelFactory by instance()

    private lateinit var loginCashierViewModel: LoginCashierViewModel

    private lateinit var binding: ActivityAddNewEmplBinding

    private var addType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewEmplBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginCashierViewModel = ViewModelProvider(
            this,
            loginCashierViewModelFactory
        ).get(LoginCashierViewModel::class.java)

        loginCashierViewModel.setListener(this)

        addType = intent.getIntExtra(EXTRA_ADD_TYPE, 0)
        window.statusBarColor = Color.WHITE
        if (addType == 0) {
            toast("TYPE == 0")
            finish()
        }

        initUI()
        addListeners()

        binding.buttonRegister.setOnClickListener {
            if (checkFields() && checkPasswords()) {
                val modelCreateCashier = ModelCreateCashier(
                    binding.userNameEditText.text.toString(),
                    binding.phoneEditText.text.toString(),
                    addType, 1, binding.passwordEditTextRegister.text.toString()
                )
                loginCashierViewModel.createNewEmpl(modelCreateCashier)
            }
        }

    }


    override fun createEmplSuccess() {
        if (addType == 2)
            toast(getString(R.string.createNewCashierSuccess))
        else
            toast(getString(R.string.createNewPackerSuccess))
        onBackPressed()
    }

    override fun createEmplFail(code: Int?) {
        toast("ERROR: $code")
    }

    private fun checkPasswords(): Boolean {
        val pwd1 = binding.passwordEditTextRegister.text.toString()
        val pwd2 = binding.passwordEditTextRegister2.text.toString()

        return if (pwd1 == pwd2) {
            true
        } else {
            binding.password2Error.text = getString(R.string.passwordMustMatch)
            binding.password2Error.visibility = View.VISIBLE
            false
        }
    }

    private fun addListeners() {
        binding.userNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.userNameEditText.text.toString().isNotEmpty()) {
                    binding.userNameError.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.addressEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.addressEditText.text.toString().isNotEmpty()) {
                    binding.addressError.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.phoneEditText.text.toString().isNotEmpty()) {
                    binding.phoneError.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.passwordEditTextRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.passwordEditTextRegister.text.toString().isNotEmpty()) {
                    binding.pwdError.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.passwordEditTextRegister2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.passwordEditTextRegister2.text.toString().isNotEmpty()) {
                    binding.password2Error.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    private fun checkFields(): Boolean {
        var ret = true

        if (binding.userNameEditText.text.toString().isEmpty()) {
            ret = false
            binding.userNameError.visibility = View.VISIBLE
        }

        if (binding.addressEditText.text.toString().isEmpty()) {
            ret = false
            binding.addressError.visibility = View.VISIBLE
        }

        if (binding.phoneEditText.text.toString().isEmpty()) {
            ret = false
            binding.phoneError.visibility = View.VISIBLE
        }

        if (binding.passwordEditTextRegister.text.toString().isEmpty()) {
            ret = false
            binding.pwdError.visibility = View.VISIBLE
        }

        if (binding.passwordEditTextRegister2.text.toString().isEmpty()) {
            ret = false
            binding.password2Error.visibility = View.VISIBLE
        }

        return ret
    }

    private fun initUI() {
        if (addType == 3) {
            binding.toolbar.title = getString(R.string.addNewPacker)
        } else {
            binding.toolbar.title = getString(R.string.addNewCashier)
        }
    }

    override fun userDataSaved() {
    }

    override fun loginFail(code: Int?) {
    }

}