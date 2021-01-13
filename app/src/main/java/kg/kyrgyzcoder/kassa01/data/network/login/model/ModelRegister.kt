package kg.kyrgyzcoder.kassa01.data.network.login.model

data class ModelRegister(
    val phone: String,
    val password: String,
    val username: String,
    val type: Int,
    val storename: String,
    val address: String,
    val email: String
)