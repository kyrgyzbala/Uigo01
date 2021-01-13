package kg.kyrgyzcoder.kassa01.data.network.login.repo

import kg.kyrgyzcoder.kassa01.data.network.ApiService
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelCheckExist
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelLogin
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelRegister
import kg.kyrgyzcoder.kassa01.util.BaseRepository

class LoginRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun login(modelLogin: ModelLogin) = safeApiCall {
        apiService.login(modelLogin)
    }

    suspend fun register(modelRegister: ModelRegister) = safeApiCall {
        apiService.register(modelRegister)
    }

    suspend fun checkIfExists(modelCheckExist: ModelCheckExist) = safeApiCall {
        apiService.checkIfExists(modelCheckExist)
    }

}