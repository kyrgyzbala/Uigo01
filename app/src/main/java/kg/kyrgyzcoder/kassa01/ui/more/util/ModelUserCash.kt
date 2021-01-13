package kg.kyrgyzcoder.kassa01.ui.more.util

data class ModelUserCash(
    val userType: Int,
    val userId: Int,
    val userUsername: String,
    val userFullname: String,
    val lastSignIn: String?
)