package com.example.myapplication.NetworkLayer

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("login")
    fun login(@Body body: Map<String, String>): Call<ResponseBody>



}