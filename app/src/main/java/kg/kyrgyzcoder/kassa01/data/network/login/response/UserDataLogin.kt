package kg.kyrgyzcoder.kassa01.data.network.login.response

data class UserDataLogin(
    val id: Int,
    val username: String,
    val address: String,
    val storename: String
)