package kg.kyrgyzcoder.uigo01.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.uigo01.data.local.UserPreferences

@Suppress("UNCHECKED_CAST")
class UserPreferencesViewModelFactory(
    private val userPreferences: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserPreferencesViewModel(userPreferences) as T
    }
}