package com.yogadimas.yogadimas_foodmarketbwa.data.networking

import com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences.UserPreference
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Ambil nilai header dinamis (misalnya, dari preferensi pengguna)
        val accessToken = UserPreference.getToken()

        val requestBuilder = originalRequest.newBuilder().apply {
            addHeader("Authorization", "Bearer $accessToken")
            addHeader("Accept", "application/json")
        }

        // Opsional, tambahkan lebih banyak header berdasarkan kondisi
        // if (accessToken != null) {}

        val newRequest = requestBuilder.build()

        return chain.proceed(newRequest)
    }

}
