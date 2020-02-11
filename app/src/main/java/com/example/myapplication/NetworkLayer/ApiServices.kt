package com.example.myapplication.NetworkLayer

import com.example.myapplication.Models.EventModels.EventsResponse
import com.example.myapplication.Models.ResponseModelData
import com.example.myapplication.Models.LoginRequestModel
import com.example.myapplication.Models.RegisterRequestModel
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @POST("auth/register")
    fun register(@Body registerRequestModel: RegisterRequestModel): Call<ResponseModelData>

    @GET("events")
    fun getEvents(@Header("type") type: Int, @Header("Authorization") authHeader: String): Call<EventsResponse>


}