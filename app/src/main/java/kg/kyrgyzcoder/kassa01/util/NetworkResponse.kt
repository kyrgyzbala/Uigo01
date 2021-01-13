package kg.kyrgyzcoder.kassa01.util

import okhttp3.ResponseBody


/**
 *  handle api errors
 */
sealed class NetworkResponse<out T> {

    data class Success<out T>(val value: T) : NetworkResponse<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : NetworkResponse<Nothing>()
}