package kg.kyrgyzcoder.kassa01.ui.more.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.user.repo.UserDataRepository

class MoreViewModelFactory(
    private val userPreferences: UserPreferences,
    private val userDataRepository: UserDataRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoreViewModel(userPreferences, userDataRepository) as T
    }

}