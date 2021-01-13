package kg.kyrgyzcoder.kassa01.ui.more.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelDayFinish
import kg.kyrgyzcoder.kassa01.data.network.user.repo.UserDataRepository
import kg.kyrgyzcoder.kassa01.ui.more.util.ModelUserCash
import kg.kyrgyzcoder.kassa01.ui.more.util.MoreListener
import kg.kyrgyzcoder.kassa01.util.NetworkResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoreViewModel(
    private val userPreferences: UserPreferences,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private var listener: MoreListener? = null

    fun setMoreListener(listener: MoreListener) {
        this.listener = listener
    }

    fun adminSignOut() = viewModelScope.launch {
        userPreferences.changeCashierStatus("")
        listener?.signedOutAdmin()
    }

    fun closeTheDay() = viewModelScope.launch {
        userPreferences.currentCashierId.collectLatest { cashierId ->
            if (cashierId != null) {
                userPreferences.finishdayid.collectLatest {finishdayid ->
                    when (val response = userDataRepository.closeTheDay(ModelDayFinish(finishdayid!!, cashierId))) {
                        is NetworkResponse.Success -> {
                            Log.d("NURIKO", "closeTheDay: ${response.value}")
                            userPreferences.closeDayCashier()
                            listener?.dayCloseSuccess()
                        }
                        is NetworkResponse.Failure -> {
                            Log.d("NURIKO", "closeTheDay: $response")
                            listener?.dayCloseFail(response.errorCode)
                        }
                    }
                }
            } else {
                listener?.cashierIdNull()
            }
        }
    }

    fun getUserData() = viewModelScope.launch {
        userPreferences.userTypeSignedIn.collectLatest { userType ->
            userPreferences.currentCashierId.collectLatest { userId ->
                userPreferences.currentCashierUsername.collectLatest { username ->
                    userPreferences.currentCashierFullName.collectLatest { fullName ->
                        userPreferences.cashierLastLogin.collectLatest {
                            Log.d("NURIKO", "getUserData: $it")
                            val model =
                                ModelUserCash(userType!!, userId!!, username!!, fullName!!, it)
                            listener?.setUserData(model)
                        }
                    }
                }
            }
        }
    }

}