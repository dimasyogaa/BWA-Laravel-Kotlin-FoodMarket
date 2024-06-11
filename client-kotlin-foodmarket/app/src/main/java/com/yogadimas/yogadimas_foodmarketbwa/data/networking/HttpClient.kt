package com.yogadimas.yogadimas_foodmarketbwa.data.networking

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.yogadimas.yogadimas_foodmarketbwa.BuildConfig
import com.yogadimas.yogadimas_foodmarketbwa.FoodMarket
import com.yogadimas.yogadimas_foodmarketbwa.utils.Helpers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient {

    // private var client: Retrofit? = null
    // private var endpoint: Endpoint? = null
    //
    // init {
    //     buildRetrofitClient()
    // }
    //
    // fun getApi(): Endpoint? {
    //     if (endpoint == null) {
    //         endpoint = client?.create(Endpoint::class.java)
    //     }
    //     return endpoint
    // }
    //
    // private fun buildRetrofitClient() {
    //     val token = UserPreference.getToken()
    //     buildRetrofitClient(token)
    // }
    //
    // fun buildRetrofitClient(token: String?) {
    //     val builder = OkHttpClient.Builder()
    //     builder.connectTimeout(10, TimeUnit.SECONDS)
    //     builder.readTimeout(10, TimeUnit.SECONDS)
    //
    //     if (BuildConfig.DEBUG) {
    //         val interceptor = HttpLoggingInterceptor()
    //         interceptor.level = HttpLoggingInterceptor.Level.BODY
    //         builder.addInterceptor(interceptor)
    //         val context = FoodMarket.instance
    //         builder.addInterceptor(ChuckerInterceptor(context))
    //     }
    //
    //     if (token != null) {
    //         Log.e("TAG", "buildRetrofitClient: $token")
    //         builder.addInterceptor(getInterceptoreWithHeader("Authorization", "Bearer $token"))
    //     }
    //
    //     builder.addInterceptor(getInterceptoreWithHeader("Accept", "application/json"))
    //
    //
    //     val okHttpClient = builder.build()
    //     client = Retrofit.Builder()
    //         .baseUrl(BuildConfig.BASE_URL + "api/")
    //         .client(okHttpClient)
    //         .addConverterFactory(GsonConverterFactory.create(Helpers.getDefaultGson()))
    //         .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    //         .build()
    //     endpoint = null
    //
    // }
    //
    // private fun getInterceptoreWithHeader(headerKey: String, headerValue: String): Interceptor {
    //     val header = HashMap<String, String>()
    //     header[headerKey] = headerValue
    //     return getInterceptoreWithHeader(header)
    // }
    //
    // private fun getInterceptoreWithHeader(headers: Map<String, String>): Interceptor {
    //     return Interceptor {
    //         val original = it.request()
    //         val builder = original.newBuilder()
    //         for ((key, value) in headers) {
    //             builder.addHeader(key, value)
    //         }
    //         builder.method(original.method, original.body)
    //         it.proceed(builder.build())
    //     }
    // }
    //
    // companion object {
    //     private val mInstance: HttpClient = HttpClient()
    //
    //     @Synchronized
    //     fun getInstance(): HttpClient {
    //         return mInstance
    //     }
    // }

    companion object {

        fun getApiService(): Endpoint {
            val okHttpClientBuilder = OkHttpClient.Builder()

            val loggingInterceptor = if (BuildConfig.DEBUG) {
                okHttpClientBuilder.addInterceptor(ChuckerInterceptor(FoodMarket.instance))
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }


            okHttpClientBuilder.apply {
                connectTimeout(5, TimeUnit.SECONDS)
                readTimeout(5, TimeUnit.SECONDS)
                addInterceptor(loggingInterceptor)
                addInterceptor(HeaderInterceptor())
            }


            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_API)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(Helpers.getDefaultGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(Endpoint::class.java)

        }

    }

}