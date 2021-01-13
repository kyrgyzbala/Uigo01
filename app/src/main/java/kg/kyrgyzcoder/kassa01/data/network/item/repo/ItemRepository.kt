package kg.kyrgyzcoder.kassa01.data.network.item.repo

import kg.kyrgyzcoder.kassa01.data.network.ApiService
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelCreateCategory
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelPostItem
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelPostTransaction
import kg.kyrgyzcoder.kassa01.util.BaseRepository

class ItemRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun postNewTransaction(modelPostTransaction: ModelPostTransaction) = safeApiCall {
        apiService.createNewTransactionAsync(modelPostTransaction)
    }

    suspend fun createNewCategory(modelCreateCategory: ModelCreateCategory) = safeApiCall {
        apiService.postNewCategory(modelCreateCategory)
    }

    suspend fun getCategoryList(page: Int) = safeApiCall {
        apiService.getCategories(page)
    }

    suspend fun postNewItems(modelPostItem: ModelPostItem, token: String) = safeApiCall {
        apiService.postNewItem(modelPostItem, token)
    }

    suspend fun getItems(storeId: Int, category: String, page: Int, token: String) = safeApiCall {
        apiService.getItems(storeId, category, page, token)
    }

    suspend fun getItemByUid(storeId: Int, uid: String, token: String) = safeApiCall {
        apiService.getItemByUid(storeId, uid, token)
    }
}