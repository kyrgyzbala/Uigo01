package kg.kyrgyzcoder.kassa01.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesImpl(context: Context) : UserPreferences {

    private val applicationContext = context.applicationContext

    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "user_data"
        )
    }

    override suspend fun saveUserData(
        firstTime: String,
        token: String,
        userName: String,
        userPhone: String,
        address: String, userId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = firstTime
            preferences[USER_TOKEN] = token
            preferences[USER_NAME] = userName
            preferences[USER_PHONE] = userPhone
            preferences[USER_ADDRESS] = address
            preferences[USER_ID_KEY] = userId
        }
    }

    override suspend fun saveCashierData(
        userName: String, id: Int, fullName: String,
        status: String, lastLogin: String, userType: Int, finishdayid: Int
    ) {
        dataStore.edit { prefs ->
            prefs[CASHIER_USERNAME_KEY] = userName
            prefs[CASHIER_FULL_NAME_KEY] = fullName
            prefs[CASHIER_ID_KEY] = id
            prefs[IS_CASHIER_SIGNED_IN] = status
            prefs[USER_TYPE_KEY] = userType
            prefs[LAST_LOGIN_TIME] = lastLogin
            prefs[FINISH_DAY_ID_KEY] = finishdayid
        }
    }

    override val finishdayid: Flow<Int?>
        get() = dataStore.data.map { pref ->
            pref[FINISH_DAY_ID_KEY]
        }

    override val userTypeSignedIn: Flow<Int?>
        get() = dataStore.data.map { pref ->
            pref[USER_TYPE_KEY]
        }

    override val cashierLastLogin: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[LAST_LOGIN_TIME]
        }

    override suspend fun changeCashierStatus(status: String) {
        dataStore.edit { prefs ->
            prefs[IS_CASHIER_SIGNED_IN] = status
        }
    }

    override suspend fun closeDayCashier() {
        dataStore.edit { prefs ->
            prefs[IS_CASHIER_SIGNED_IN] = ""
        }
    }

    override val isCashierSignedIn: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[IS_CASHIER_SIGNED_IN]
        }

    override val currentCashierFullName: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[CASHIER_FULL_NAME_KEY]
        }

    override val currentCashierId: Flow<Int?>
        get() = dataStore.data.map { pref ->
            pref[CASHIER_ID_KEY]
        }

    override val currentCashierUsername: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[CASHIER_USERNAME_KEY]
        }

    override val userId: Flow<Int?>
        get() = dataStore.data.map { pref ->
            pref[USER_ID_KEY]
        }

    override val userName: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[USER_NAME]
        }

    override val userPhone: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[USER_PHONE]
        }

    override val userToken: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[USER_TOKEN]
        }

    override val userAddress: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[USER_ADDRESS]
        }

    override val isLoggedIn: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN]
        }

    companion object {
        private val IS_LOGGED_IN = preferencesKey<String>("is_logged_in")
        private val USER_TOKEN = preferencesKey<String>("user_token")
        private val USER_NAME = preferencesKey<String>("user_name")
        private val USER_PHONE = preferencesKey<String>("user_phone")
        private val USER_ADDRESS = preferencesKey<String>("user_address")
        private val USER_ID_KEY = preferencesKey<Int>("user_key")

        private val CASHIER_ID_KEY = preferencesKey<Int>("cashier_id_key")
        private val CASHIER_USERNAME_KEY = preferencesKey<String>("cashier_username_key")
        private val CASHIER_FULL_NAME_KEY = preferencesKey<String>("cashier_full_name_key")
        private val IS_CASHIER_SIGNED_IN = preferencesKey<String>("is_cashier_signed_in")
        private val LAST_LOGIN_TIME = preferencesKey<String>("last_login_time")
        private val USER_TYPE_KEY = preferencesKey<Int>("user_type_key")
        private val FINISH_DAY_ID_KEY = preferencesKey<Int>("finish_day_id_key")
    }

}