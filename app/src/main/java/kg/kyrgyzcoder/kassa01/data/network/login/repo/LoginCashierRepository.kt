package kg.kyrgyzcoder.kassa01.data.network.login.repo

import kg.kyrgyzcoder.kassa01.data.network.ApiService
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCashLogin
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCreateCashier
import kg.kyrgyzcoder.kassa01.util.BaseRepository

class LoginCashierRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun loginCashier(modelLogin: ModelCashLogin) = safeApiCall {
        apiService.cashierLogin(modelLogin)
    }

    suspend fun createNewEmpl(modelCreateCashier: ModelCreateCashier) = safeApiCall {
        apiService.createNewEmpl(modelCreateCashier)
    }

}