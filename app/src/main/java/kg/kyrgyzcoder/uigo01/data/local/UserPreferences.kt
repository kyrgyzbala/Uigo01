package kg.kyrgyzcoder.uigo01.data.local

import kotlinx.coroutines.flow.Flow

interface UserPreferences {

    suspend fun saveIsFirstTime(text: String)
    val isFirstTime: Flow<String?>

}