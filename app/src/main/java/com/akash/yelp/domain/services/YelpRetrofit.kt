package com.akash.yelp.domain.services

import com.akash.yelp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class YelpRetrofit {
    val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ApiKeyInterceptor())
                    .build()
            )
            .baseUrl("https://api.yelp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private class ApiKeyInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + BuildConfig.API_KEY)
                    .build()
            )
        }
    }
}