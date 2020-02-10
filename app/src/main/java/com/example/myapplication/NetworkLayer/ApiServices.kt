package com.example.myapplication.NetworkLayer

import com.example.myapplication.Models.ResponseModelData
import com.example.myapplication.Models.LoginRequestModel
import com.example.myapplication.Models.RegisterRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @POST("auth/register")
    fun register(@Body registerRequestModel: RegisterRequestModel): Call<ResponseModelData>


}