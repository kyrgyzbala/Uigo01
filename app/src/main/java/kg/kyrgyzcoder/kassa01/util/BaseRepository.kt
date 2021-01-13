package kg.kyrgyzcoder.kassa01.util

import retrofit2.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException


abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResponse.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkResponse.Failure(
                            false, throwable.code(), throwable.response()?.errorBody()
                        )
                    }
                    is SocketTimeoutException -> {
                        NetworkResponse.Failure(
                            false, 1, null
                        )
                    }
                    is ConnectException ->{
                        NetworkResponse.Failure(
                            false, 2, null
                        )
                    }
                    else -> {
                        NetworkResponse.Failure(true, null, null)
                    }
                }
            }
        }
    }
}