package kg.kyrgyzcoder.kassa01.ui.login

interface LoginCashierListener {

    fun userDataSaved()
    fun loginFail(code: Int?)

    fun createEmplSuccess()
    fun createEmplFail(code: Int?)
}