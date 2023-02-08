package com.mitocode.marketappmitocodegrupo2.data

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(private val sharePreferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val token = sharePreferences.getString("TOKEN", "") ?: ""
        token?.let {
            request = request
                .newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()
        }

        return chain.proceed(request)
    }
}