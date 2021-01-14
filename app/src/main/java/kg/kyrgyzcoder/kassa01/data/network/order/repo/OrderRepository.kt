package kg.kyrgyzcoder.kassa01.data.network.order.repo

import kg.kyrgyzcoder.kassa01.data.network.ApiService2
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelOrderDel
import kg.kyrgyzcoder.kassa01.util.BaseRepository

class OrderRepository(
    private val apiService2: ApiService2
) : BaseRepository() {

    suspend fun callDelivery(modelOrderDel: ModelOrderDel) = safeApiCall {
        apiService2.callDelivery(modelOrderDel)
    }

}