package kg.kyrgyzcoder.kassa01.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelCheckExist
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelLogin
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelRegister
import kg.kyrgyzcoder.kassa01.data.network.login.repo.LoginRepository
import kg.kyrgyzcoder.kassa01.data.network.login.response.ModelLoginResponse
import kg.kyrgyzcoder.kassa01.ui.login.LoginListener
import kg.kyrgyzcoder.kassa01.util.NetworkResponse
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {


    private var listener: LoginListener? = null

    fun setListener(listener: LoginListener) {
        this.listener = listener
    }


    fun registerNewUser(modelRegister: ModelRegister) = viewModelScope.launch {
        when (val response =
            loginRepository.checkIfExists(ModelCheckExist(modelRegister.username))) {
            is NetworkResponse.Success -> {
                if (response.value.Success)
                    listener?.userExists()
                else
                    register(modelRegister)
            }
        }
    }

    private fun register(modelRegister: ModelRegister) = viewModelScope.launch {
        when (val response = loginRepository.register(modelRegister)) {
            is NetworkResponse.Success -> {
                val res = response.value
                login(ModelLogin(res.username, modelRegister.password))
            }
            is NetworkResponse.Failure -> {
                listener?.loginFail(response.errorCode)
            }
        }
    }

    fun login(modelLogin: ModelLogin) = viewModelScope.launch {
        when (val response = loginRepository.login(modelLogin)) {
            is NetworkResponse.Success -> {
                saveUserData(response.value)
            }
            is NetworkResponse.Failure -> {
                listener?.loginFail(response.errorCode)
            }
        }
    }

    private fun saveUserData(modelLoginResponse: ModelLoginResponse) = viewModelScope.launch {
        val user = modelLoginResponse.data[0]
        userPreferences.saveUserData(
            "DONE",
            modelLoginResponse.token,
            user.storename,
            user.username,
            user.address, user.id
        )
        listener?.loginSuccess()
    }

}