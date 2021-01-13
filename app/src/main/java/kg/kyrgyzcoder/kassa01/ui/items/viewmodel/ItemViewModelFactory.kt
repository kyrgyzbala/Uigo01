package kg.kyrgyzcoder.kassa01.ui.items.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.item.repo.ItemRepository

class ItemViewModelFactory(
    private val itemRepository: ItemRepository,
    private val userPreferences: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemViewModel(itemRepository, userPreferences) as T
    }

}