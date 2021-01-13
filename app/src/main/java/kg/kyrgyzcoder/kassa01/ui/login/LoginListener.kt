package kg.kyrgyzcoder.kassa01.ui.login

interface LoginListener {

    fun loginSuccess()
    fun loginFail(code: Int?)
    fun userExists()
}