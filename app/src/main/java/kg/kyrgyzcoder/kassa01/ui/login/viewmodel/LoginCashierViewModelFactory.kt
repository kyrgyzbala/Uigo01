package kg.kyrgyzcoder.kassa01.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.login.repo.LoginCashierRepository

class LoginCashierViewModelFactory(
    private val userPreferences: UserPreferences,
    private val loginCashierRepository: LoginCashierRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginCashierViewModel(userPreferences, loginCashierRepository) as T
    }

}