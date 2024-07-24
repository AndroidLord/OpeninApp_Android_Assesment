package com.example.openinappandroidassesment.api

import com.example.openinappandroidassesment.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    val openApp_API = getInstance().create(OpenInAppAPI::class.java)

}