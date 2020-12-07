package kg.kyrgyzcoder.uigo01.ui.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kg.kyrgyzcoder.uigo01.R
import kg.kyrgyzcoder.uigo01.ui.main.MainActivity
import kg.kyrgyzcoder.uigo01.ui.splash.viewmodel.UserPreferencesViewModel
import kg.kyrgyzcoder.uigo01.ui.splash.viewmodel.UserPreferencesViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SplashScreenActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val userPrefsViewModelFactory: UserPreferencesViewModelFactory by instance()

    private lateinit var userPrefsViewModel: UserPreferencesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        userPrefsViewModel =
            ViewModelProvider(this, userPrefsViewModelFactory).get(UserPreferencesViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        GlobalScope.launch(Dispatchers.Main) {
            delayFor()
        }
    }

    private suspend fun delayFor() {
        delay(2000)
        userPrefsViewModel.isUserFirstTime.asLiveData().observe(this, Observer { isLoggedIn ->
            if (isLoggedIn.isNullOrEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}