package kg.kyrgyzcoder.kassa01.ui.orders.utils

interface OrdersListener {
    fun deliveryCallSuccess()
    fun deliveryCallFail(code: Int?)

    fun setUserAddressPhone(address: String?, phone: String?)
}