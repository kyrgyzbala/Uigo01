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
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelLogin
import kg.kyrgyzcoder.kassa01.databinding.ActivityLoginBinding
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginViewModel
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginViewModelFactory
import kg.kyrgyzcoder.kassa01.util.hide
import kg.kyrgyzcoder.kassa01.util.hideKeyboard
import kg.kyrgyzcoder.kassa01.util.show
import kg.kyrgyzcoder.kassa01.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware, LoginListener {

    override val kodein: Kodein by closestKodein()
    private val loginViewModelFactory: LoginViewModelFactory by instance()

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE
        binding.buttonLogin.alpha = 0.4F
        loginViewModel =
            ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
        loginViewModel.setListener(this)

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        addListeners()

        binding.buttonLogin.setOnClickListener {
            if (binding.buttonLogin.alpha == 1f) {
                if (checkInputs()) {
                    hideKeyboard()
                    binding.progressBar.show()
                    val modelLogin = ModelLogin(
                        binding.userNameEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    )
                    loginViewModel.login(modelLogin)
                }
            }

        }

    }

    override fun loginSuccess() {
        binding.progressBar.hide()
        toast(getString(R.string.welcome))
        val intent = Intent(this, CashierLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(intent)
    }

    override fun loginFail(code: Int?) {
        binding.progressBar.hide()
        if (code == 400) {
            binding.passwordError.text = getString(R.string.pwdUserNameWrong)
            binding.passwordError.visibility = View.VISIBLE
        } else {
            toast(getString(R.string.errorTryAgain))
        }
    }

    override fun userExists() {
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
                        binding.buttonLogin.alpha = 0.4F
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
                        binding.buttonLogin.alpha = 0.4F
                    }
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
}