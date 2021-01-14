package kg.kyrgyzcoder.kassa01.data.network.order.model

data class ModelOrderDel(
    val login: String = "Smartpost",
    val password: String = "38rLY\$KJasdfass#@C8&HV",
    val description: String = "От Нурика",
    var address_from: String = "",
    var address_to: String = "",
    val customer_amount: Int = 0,
    var customer_name: String = "",
    var customer_phone: String = "",
    val receiver_name: String,
    val receiver_phone: String,
    val pays_receiver: Boolean = true,
    val partner_order_id: Int = 1
)