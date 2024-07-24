package com.example.openinappandroidassesment.api

import com.example.openinappandroidassesment.models.DashBoardModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface OpenInAppAPI {

    @GET("/api/v1/dashboardNew")
    suspend fun getDashBoard(
        @Header("Authorization") token: String
    ) : Response<DashBoardModel>

}