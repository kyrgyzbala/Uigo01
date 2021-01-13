package kg.kyrgyzcoder.kassa01.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.login.repo.LoginRepository

class LoginViewModelFactory(
    private val loginRepository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginRepository, userPreferences) as T
    }
}