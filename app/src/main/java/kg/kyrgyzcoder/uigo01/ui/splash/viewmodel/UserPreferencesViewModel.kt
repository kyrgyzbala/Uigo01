package kg.kyrgyzcoder.uigo01.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import kg.kyrgyzcoder.uigo01.data.local.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserPreferencesViewModel (private  val userPrefs: UserPreferences): ViewModel() {

    val isUserFirstTime = userPrefs.isFirstTime

    fun saveIsFirstTime(text: String) {
        GlobalScope.launch(Dispatchers.Main) {
            userPrefs.saveIsFirstTime(text)
        }
    }

}