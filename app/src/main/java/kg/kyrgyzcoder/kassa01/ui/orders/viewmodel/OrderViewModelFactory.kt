package kg.kyrgyzcoder.kassa01.ui.orders.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.order.repo.OrderRepository

class OrderViewModelFactory(
    private val ordersRepository: OrderRepository,
    private val userDataLocal: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderViewModel(ordersRepository, userDataLocal) as T
    }
}