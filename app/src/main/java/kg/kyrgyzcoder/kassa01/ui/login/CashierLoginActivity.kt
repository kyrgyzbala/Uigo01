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
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCashLogin
import kg.kyrgyzcoder.kassa01.databinding.ActivityCashierLoginBinding
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginCashierViewModel
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginCashierViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.main.MainActivity
import kg.kyrgyzcoder.kassa01.util.hideKeyboard
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class CashierLoginActivity : AppCompatActivity(), KodeinAware, LoginCashierListener {

    override val kodein: Kodein by closestKodein()
    private val loginCashierViewModelFactory: LoginCashierViewModelFactory by instance()

    private lateinit var loginCashierViewModel: LoginCashierViewModel

    private lateinit var binding: ActivityCashierLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashierLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE
        binding.buttonLogin.alpha = 0.4F

        loginCashierViewModel = ViewModelProvider(
            this,
            loginCashierViewModelFactory
        ).get(LoginCashierViewModel::class.java)
        loginCashierViewModel.setListener(this)

        addListeners()

        binding.buttonLogin.setOnClickListener {
            if (binding.buttonLogin.alpha == 1f) {
                if (checkInputs()) {
                    hideKeyboard()
                    binding.progressBar.show()
                    loginCashierViewModel.loginCashier(
                        ModelCashLogin(
                            binding.userNameEditText.text.toString(),
                            binding.passwordEditText.text.toString()
                        )
                    )
                }
            }
        }

    }

    private fun addListeners() {
        binding.userNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = binding.userNameEditText.text.toString()
                if (text.isNotEmpty()) {
                    binding.userNameError.visibility = View.GONE
                    if (binding.passwordEditText.text.toString().length >= 6) {
                        binding.buttonLogin.alpha = 1F
                    } else {
                        binding.buttonLogin.alpha = 0.5f
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.passwordEditText.text.toString().length >= 6) {
                    binding.passwordError.visibility = View.GONE
                    val text = binding.userNameEditText.text.toString()
                    if (text.isNotEmpty()) {
                        binding.buttonLogin.alpha = 1F
                    } else {
                        binding.buttonLogin.alpha = 0.5f
                    }
                } else {
                    binding.buttonLogin.alpha = 0.5f
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun checkInputs(): Boolean {
        var ret = true

        if (binding.userNameEditText.text.toString().isEmpty()) {
            binding.userNameError.visibility = View.VISIBLE
            ret = false
        }
        val password = binding.passwordEditText.text.toString()
        if (password.isEmpty() || password.length < 6) {
            ret = false
            binding.passwordError.visibility = View.VISIBLE
            binding.passwordError.text = getString(R.string.mustContainSix)
        }

        return ret
    }

    override fun userDataSaved() {
        binding.progressBar.hide()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(intent)
    }

    override fun loginFail(code: Int?) {
        binding.passwordError.visibility = View.VISIBLE
        binding.passwordError.text = getString(R.string.pwdUserNameWrong)
        binding.progressBar.hide()
    }

    override fun createEmplSuccess() {

    }

    override fun createEmplFail(code: Int?) {
    }

}