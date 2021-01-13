package kg.kyrgyzcoder.kassa01.data.network.user.repo

import kg.kyrgyzcoder.kassa01.data.network.ApiService
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelDayFinish
import kg.kyrgyzcoder.kassa01.util.BaseRepository

class UserDataRepository (
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun closeTheDay(modelDayFinish: ModelDayFinish) = safeApiCall {
        apiService.closeTheDay(modelDayFinish)
    }

}