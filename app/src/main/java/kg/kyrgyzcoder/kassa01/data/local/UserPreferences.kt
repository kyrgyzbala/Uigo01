package kg.kyrgyzcoder.kassa01.data.local

import kotlinx.coroutines.flow.Flow

interface UserPreferences {

    val isLoggedIn: Flow<String?>

    val userName: Flow<String?>
    val userPhone: Flow<String?>
    val userToken: Flow<String?>
    val userAddress: Flow<String?>
    val userId: Flow<Int?>

    val isCashierSignedIn: Flow<String?>
    val currentCashierUsername: Flow<String?>
    val currentCashierId: Flow<Int?>
    val currentCashierFullName: Flow<String?>
    val cashierLastLogin: Flow<String?>
    val userTypeSignedIn: Flow<Int?>
    val finishdayid: Flow<Int?>

    suspend fun changeCashierStatus(status: String)
    suspend fun closeDayCashier()

    suspend fun saveUserData(
        firstTime: String, token: String, userName: String,
        userPhone: String, address: String, userId: Int
    )

    suspend fun saveCashierData(
        userName: String, id: Int, fullName: String, status: String,
        lastLogin: String, userType: Int, finishdayid: Int
    )
}