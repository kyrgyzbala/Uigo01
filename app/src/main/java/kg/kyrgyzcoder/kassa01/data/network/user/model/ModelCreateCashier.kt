package kg.kyrgyzcoder.kassa01.data.network.user.model

data class ModelCreateCashier(
    val fullname: String,
    val phone: String,
    val usertype: Int,
    var store: Int = 1,
    val password: String
)