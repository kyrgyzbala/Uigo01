package kg.kyrgyzcoder.kassa01.ui.items.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelCreateCategory
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelPostItem
import kg.kyrgyzcoder.kassa01.data.network.item.repo.ItemRepository
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelPostTransaction
import kg.kyrgyzcoder.kassa01.ui.items.util.EditItemListener
import kg.kyrgyzcoder.kassa01.ui.items.util.ItemListener
import kg.kyrgyzcoder.kassa01.util.NetworkResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemViewModel(
    private val itemRepository: ItemRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private var listener: ItemListener? = null
    private var listenerEdit: EditItemListener? = null

    fun setEditListener(listener: EditItemListener) {
        this.listenerEdit = listener
    }

    fun setListener(listener: ItemListener) {
        this.listener = listener
    }

    fun getUserType() = viewModelScope.launch {
        userPreferences.userTypeSignedIn.collectLatest {
            listener?.setUserType(it!!)
        }
    }

    fun adminSignOut() = viewModelScope.launch {
        userPreferences.changeCashierStatus("")
        listener?.adminSignedOut()
    }

    fun postNewTransAction(modelPostTransaction: ModelPostTransaction) = viewModelScope.launch {

        userPreferences.currentCashierId.collectLatest { cashierId ->
            modelPostTransaction.cashier = cashierId!!
            when (val response = itemRepository.postNewTransaction(modelPostTransaction)) {
                is NetworkResponse.Success -> {
                    listener?.setReceiptSuccess(response.value)
                    Log.d("NURIKO", "postNewTransAction: response: $response")
                }
                is NetworkResponse.Failure -> {
                    listener?.getReceiptFail(response.errorCode)
                    Log.d("NURIKO", "postNewTransAction: response: $response")
                }
            }
        }


    }

    fun getItemByUid(uid: String) = viewModelScope.launch {
        userPreferences.userId.collectLatest {
            userPreferences.userToken.collectLatest { token ->
                when (val response = itemRepository.getItemByUid(it!!, uid, "Token $token")) {
                    is NetworkResponse.Success -> {
                        Log.d("NURIKO", "getItemByUid: SUCCESS")
                        listener?.setItemByUid(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        Log.d("NURIKO", "getItemByUid: FAIL")
                        listener?.setItemByUidFail(response.errorCode)
                    }
                }
            }
        }
    }

    fun getItem(page: Int, category: String) = viewModelScope.launch {
        userPreferences.userId.collectLatest {
            userPreferences.userToken.collectLatest { token ->
                when (val response =
                    itemRepository.getItems(it!!, category, page, "Token $token")) {
                    is NetworkResponse.Success -> {
                        listener?.setItems(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        listener?.getItemFail(response.errorCode)
                    }
                }
            }
        }
    }

    fun postNewItem(modelPostItem: ModelPostItem) = viewModelScope.launch {
        userPreferences.userId.collectLatest {
            modelPostItem.storeid = it!!
            userPreferences.userToken.collectLatest { token ->
                when (val response = itemRepository.postNewItems(modelPostItem, "Token $token")) {
                    is NetworkResponse.Success -> {
                        listener?.postItemSuccess(response.value.Success)
                    }
                    is NetworkResponse.Failure -> {
                        listener?.postItemFail(response.errorCode)
                    }
                }
            }
        }
    }

    fun getCategories(page: Int) = viewModelScope.launch {
        Log.d("NURIKO", "getCategories: CALL")
        when (val response = itemRepository.getCategoryList(page)) {
            is NetworkResponse.Success -> {
                Log.d("NURIKO", "getCategories: SUCCESS")
                listener?.setCategories(response.value)
            }
            is NetworkResponse.Failure -> {
                Log.d("NURIKO", "getCategories: FAIL")
                listener?.getCategoryFail(response.errorCode)
            }
        }
    }

    fun createNewCategory(modelCreateCategory: ModelCreateCategory) = viewModelScope.launch {
        userPreferences.userId.collectLatest {
            modelCreateCategory.storeid = it!!
            when (val response = itemRepository.createNewCategory(modelCreateCategory)) {
                is NetworkResponse.Success -> {
                    listener?.createCategorySuccess()
                }
                is NetworkResponse.Failure -> {
                    listener?.createCategoryFail(response.errorCode)
                }
            }
        }
    }

}