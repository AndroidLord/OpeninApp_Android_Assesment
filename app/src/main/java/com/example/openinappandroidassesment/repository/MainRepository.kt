package com.example.openinappandroidassesment.repository

import com.example.openinappandroidassesment.api.RetrofitInstance.openApp_API
import com.example.openinappandroidassesment.models.DashBoardModel
import retrofit2.Response


class MainRepository {

    suspend fun getDashBoard(token: String): Response<DashBoardModel> {
        return openApp_API.getDashBoard(token)
    }

}