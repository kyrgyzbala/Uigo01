package kg.kyrgyzcoder.kassa01.ui.items.util

import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelActivePag
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelCategoryPag

interface ItemListener {

    fun createCategorySuccess()
    fun createCategoryFail(code: Int?)

    fun setCategories(cats: ModelCategoryPag)
    fun getCategoryFail(code: Int?)

    fun postItemSuccess(status: Boolean)
    fun postItemFail(code: Int?)

    fun setItems(itemPag: ModelActivePag)
    fun getItemFail(code: Int?)

    fun setItemByUid(modelActivePag: ModelActivePag)
    fun setItemByUidFail(code: Int?)

    fun setUserType(userType: Int)
    fun adminSignedOut()

    fun setReceiptSuccess(html: String)
    fun getReceiptFail(code: Int?)
}