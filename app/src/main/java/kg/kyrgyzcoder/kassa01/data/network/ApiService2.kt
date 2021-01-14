package kg.kyrgyzcoder.kassa01.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelOrderDel
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelOrderDelResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService2 {

    @POST("api/v1/order/create/for/smarpost/")
    suspend fun callDelivery(@Body modelOrderDel: ModelOrderDel): ModelOrderDelResponse

    companion object {
        private const val BASE_URL = "http://admin-postman.smartpost.kg/"

        operator fun invoke(): ApiService2 {
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
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService2::class.java)
        }
    }

}