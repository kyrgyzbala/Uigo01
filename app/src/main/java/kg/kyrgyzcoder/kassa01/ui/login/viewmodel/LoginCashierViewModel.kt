package kg.kyrgyzcoder.kassa01.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.login.repo.LoginCashierRepository
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCashLogin
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCashier
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCreateCashier
import kg.kyrgyzcoder.kassa01.ui.login.LoginCashierListener
import kg.kyrgyzcoder.kassa01.util.NetworkResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginCashierViewModel(
    private val userPreferences: UserPreferences,
    private val loginCashierRepository: LoginCashierRepository
) : ViewModel() {

    private var listener: LoginCashierListener? = null

    fun setListener(listener: LoginCashierListener) {
        this.listener = listener
    }

    fun loginCashier(modelLogin: ModelCashLogin) = viewModelScope.launch {
        when (val response = loginCashierRepository.loginCashier(modelLogin)) {
            is NetworkResponse.Success -> {
                saveCashierData(response.value)
            }
            is NetworkResponse.Failure -> {
                listener?.loginFail(response.errorCode)
            }
        }
    }

    fun createNewEmpl(modelCreateCashier: ModelCreateCashier) = viewModelScope.launch {
        userPreferences.userId.collectLatest {
            modelCreateCashier.store = it!!
            when (val response = loginCashierRepository.createNewEmpl(modelCreateCashier)) {
                is NetworkResponse.Success -> {
                    listener?.createEmplSuccess()
                }
                is NetworkResponse.Failure -> {
                    listener?.loginFail(response.errorCode)
                }
            }
        }
    }

    private fun saveCashierData(value: List<ModelCashier>) = viewModelScope.launch {
        val modelCashier = value[0]
        Log.d("NURIKO", "saveCashierData: ${modelCashier.lastdate}")
        userPreferences.saveCashierData(
            modelCashier.phone, modelCashier.id,
            modelCashier.fullname, "DONE",
            modelCashier.lastdate, modelCashier.usertype, modelCashier.finishdayid
        )
        listener?.userDataSaved()
    }


}