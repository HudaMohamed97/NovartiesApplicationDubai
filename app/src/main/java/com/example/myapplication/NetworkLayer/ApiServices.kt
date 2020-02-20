package com.example.myapplication.NetworkLayer

import com.example.myapplication.Models.*
import com.example.myapplication.Models.EventModels.EventsResponse
import com.example.myapplication.Models.EventModels.SingleEventResponse
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

    @POST("events/{event}/attendance")
    fun submitAttendance(@Body body: Map<String, String>, @Path("event") eventId: Int, @Header("Authorization") authHeader: String): Call<submitModel>

    //this to get All Events

    @GET("events")
    fun getSessions(@Header("Authorization") authHeader: String): Call<SessionsResponse>

    @GET("articles")
    fun getArticles(): Call<ResponseBody>

    @GET("polls")
    fun getPolls(@Header("Authorization") authHeader: String): Call<PollModel>

    @GET("polls/{poll}")
    fun getSingelPolls(@Path("poll") pollId: Int, @Header("Authorization") authHeader: String): Call<PollModel>

    @POST("polls")
    fun submitPolls(@Body body: Map<String, String>, @Header("Authorization") authHeader: String): Call<PollModel>

    @GET("speakers")
    fun getSpeakers(@Header("Authorization") authHeader: String): Call<SpeakersResponseModel>

    @GET("agenda")
    fun getAgenda(@Header("Authorization") authHeader: String): Call<AgendaModelResponse>

    @GET("articles/{article}")
    fun getSingleArticle(@Path("article") eventId: Int): Call<ResponseBody>


    // get single event data
    @GET("events/{event}")
    fun getSingleEventData(@Path("event") eventId: Int, @Header("type") type: Int, @Header("Authorization") authHeader: String): Call<SingleEventResponse>


}