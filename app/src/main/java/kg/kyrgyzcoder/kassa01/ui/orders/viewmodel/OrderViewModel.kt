package kg.kyrgyzcoder.kassa01.ui.orders.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelOrderDel
import kg.kyrgyzcoder.kassa01.data.network.order.repo.OrderRepository
import kg.kyrgyzcoder.kassa01.ui.orders.utils.OrdersListener
import kg.kyrgyzcoder.kassa01.util.NetworkResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderViewModel(
    private val ordersRepository: OrderRepository,
    private val userDataLocal: UserPreferences
) : ViewModel() {

    private var listener: OrdersListener? = null

    fun setListener(listener: OrdersListener) {
        this.listener = listener
    }

    fun getUserPhoneAddress() = viewModelScope.launch {
        userDataLocal.userPhone.collectLatest { userPhone ->
            userDataLocal.userAddress.collectLatest {
                listener?.setUserAddressPhone(it, userPhone)
            }
        }
    }

    fun callDelivery(modelOrderDel: ModelOrderDel) = viewModelScope.launch {
        userDataLocal.userName.collectLatest {
            modelOrderDel.customer_name = it ?: "NoName"
            when (val response = ordersRepository.callDelivery(modelOrderDel)) {
                is NetworkResponse.Success -> {
                    listener?.deliveryCallSuccess()
                }
                is NetworkResponse.Failure -> {
                    listener?.deliveryCallFail(response.errorCode)
                }
            }
        }
    }

}