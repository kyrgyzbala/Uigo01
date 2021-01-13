package kg.kyrgyzcoder.kassa01.data.network.login.response

data class ModelRegisterResponse(
    val username: String,
    val type: Int,
    val address: String,
    val phone: String
)