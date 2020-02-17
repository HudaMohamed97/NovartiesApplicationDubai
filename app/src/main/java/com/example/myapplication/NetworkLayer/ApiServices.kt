package com.example.myapplication.NetworkLayer

import com.example.myapplication.Models.EventModels.EventsResponse
import com.example.myapplication.Models.EventModels.SingleEventResponse
import com.example.myapplication.Models.ResponseModelData
import com.example.myapplication.Models.LoginRequestModel
import com.example.myapplication.Models.RegisterRequestModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @POST("auth/register")
    fun register(@Body registerRequestModel: RegisterRequestModel): Call<ResponseModelData>

    @POST("account/update")
    fun updateAccount(@Body registerRequestModel: RegisterRequestModel): Call<ResponseModelData>

    //this to get All Events

    @GET("events")
    fun getEvents(@Header("type") type: Int, @Header("Authorization") authHeader: String): Call<EventsResponse>

    @GET("articles")
    fun getArticles(): Call<ResponseBody>

    @GET("articles/{article}")
    fun getSingleArticle(@Path("article") eventId: Int): Call<ResponseBody>


    // get single event data
    @GET("events/{event}")
    fun getSingleEventData(@Path("event") eventId: Int, @Header("type") type: Int, @Header("Authorization") authHeader: String): Call<SingleEventResponse>


}