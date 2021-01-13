package kg.kyrgyzcoder.kassa01.data.network.login.response

data class ModelLoginResponse(
    val token: String,
    val data: List<UserDataLogin>
)