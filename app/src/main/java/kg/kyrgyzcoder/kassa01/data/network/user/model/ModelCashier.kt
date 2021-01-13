package kg.kyrgyzcoder.kassa01.data.network.user.model

data class ModelCashier(
    val id: Int,
    val fullname: String,
    val phone: String,
    val usertype: Int,
    val store: Int,
    val lastdate: String,
    val finishdayid: Int
)