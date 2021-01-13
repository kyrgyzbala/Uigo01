package kg.kyrgyzcoder.kassa01.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences

class UserPreferencesViewModel (private  val userPrefs: UserPreferences): ViewModel() {

    val isUserFirstTime = userPrefs.isLoggedIn

    val isCashierSignedIn = userPrefs.isCashierSignedIn
}