package com.example.openinappandroidassesment.api

import com.example.openinappandroidassesment.utils.BASE_URL
import com.example.openinappandroidassesment.utils.TOKEN
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build();
    }

    fun getOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer ${TOKEN}")
                .method(original.method, original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }.addInterceptor(logging).build()
    }

    val openApp_API = getInstance().create(OpenInAppAPI::class.java)

}