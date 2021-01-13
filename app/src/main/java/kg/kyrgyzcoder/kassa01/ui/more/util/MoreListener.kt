package kg.kyrgyzcoder.kassa01.ui.more.util

interface MoreListener {

    fun setUserData(modelUserCash: ModelUserCash)
    fun signedOutAdmin()

    fun cashierIdNull()
    fun dayCloseSuccess()
    fun dayCloseFail(code: Int?)
}