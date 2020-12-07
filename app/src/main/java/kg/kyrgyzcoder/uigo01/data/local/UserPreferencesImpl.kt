package kg.kyrgyzcoder.uigo01.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesImpl (context: Context) : UserPreferences {

    private val applicationContext = context.applicationContext

    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "user_data"
        )
    }

    override suspend fun saveIsFirstTime(text: String) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = text
        }
    }

    override val isFirstTime: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN]
        }

    companion object {
        private var IS_LOGGED_IN = preferencesKey<String>("is_logged_in")
    }

}