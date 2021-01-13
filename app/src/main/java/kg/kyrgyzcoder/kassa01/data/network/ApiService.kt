package kg.kyrgyzcoder.kassa01.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kg.kyrgyzcoder.kassa01.data.network.item.model.*
import kg.kyrgyzcoder.kassa01.data.network.item.response.ModelCreateCategoryResponse
import kg.kyrgyzcoder.kassa01.data.network.item.response.ModelPostItemResponse
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelCheckExist
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelLogin
import kg.kyrgyzcoder.kassa01.data.network.login.model.ModelRegister
import kg.kyrgyzcoder.kassa01.data.network.login.response.ModelCheckExistResponse
import kg.kyrgyzcoder.kassa01.data.network.login.response.ModelLoginResponse
import kg.kyrgyzcoder.kassa01.data.network.login.response.ModelRegisterResponse
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelCloseDayResponse
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelDayFinish
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelPostTransaction
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCashLogin
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCashier
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCreateCashier
import kg.kyrgyzcoder.kassa01.data.network.user.model.ModelCreateCashierResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST("api/store/login/")
    suspend fun login(@Body modelLogin: ModelLogin): ModelLoginResponse

    @POST("api/store/register/")
    suspend fun register(@Body modelRegister: ModelRegister): ModelRegisterResponse

    @POST("api/store/checkusername/")
    suspend fun checkIfExists(@Body modelCheckExist: ModelCheckExist): ModelCheckExistResponse

    @GET("api/item/getcategory/")
    suspend fun getCategories(
        @Query("page") page: Int
    ): ModelCategoryPag

    @POST("api/item/category/")
    suspend fun postNewCategory(@Body modelCreateCategory: ModelCreateCategory): ModelCreateCategoryResponse

    @POST("api/item/newitem/")
    suspend fun postNewItem(
        @Body modelPostItem: ModelPostItem,
        @Header("Authorization") token: String
    ): ModelPostItemResponse

    @GET("api/item/activeitems/")
    suspend fun getItems(
        @Query("storeid") storeId: Int,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): ModelActivePag

    @GET("api/item/activeitems/")
    suspend fun getItemByUid(
        @Query("storeid") storeId: Int,
        @Query("uniqueid") uid: String,
        @Header("Authorization") token: String
    ): ModelActivePag

    @POST ("api/store/logincashier/")
    suspend fun cashierLogin(@Body modelLogin: ModelCashLogin) : List<ModelCashier>

    @POST("api/store/registercashier/")
    suspend fun createNewEmpl(@Body modelCreateCashier: ModelCreateCashier) : ModelCreateCashierResponse

    @POST("api/item/clientorder/")
    suspend fun createNewTransactionAsync(
        @Body modelPostTransaction: ModelPostTransaction
    ): String

    @POST("api/store/sendcashierid/")
    suspend fun closeTheDay(@Body modelDayFinish: ModelDayFinish): ModelCloseDayResponse

    companion object {
        private const val BASE_URL = "http://165.22.93.189/pyapps/venv/"

        operator fun invoke(): ApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}