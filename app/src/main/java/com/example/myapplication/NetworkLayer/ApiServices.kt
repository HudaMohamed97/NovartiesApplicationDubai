package com.example.myapplication.NetworkLayer

import com.example.myapplication.Models.*
import com.example.myapplication.Models.EventModels.SingleEventResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @POST("auth/reset-password")
    fun reset(@Body body: Map<String, String>): Call<SubmitModel>

    @POST("auth/register")
    fun register(@Body registerRequestModel: RegisterRequestModel): Call<ResponseModelData>

    @POST("account/update-password")
    fun updatePassword(@Body body: Map<String, String>, @Header("Authorization") authHeader: String): Call<Account>

    @Multipart
    @POST("account/update")
    fun updateAccount(
        @Part("email") email: RequestBody, @Part("name") name: RequestBody, @Part image: MultipartBody.Part,
        @Header("Authorization") authHeader: String
    ): Call<updateDataModel>

    @POST("events/{event}/attendance")
    fun submitAttendance(@Body body: Map<String, String>, @Path("event") eventId: Int, @Header("Authorization") authHeader: String): Call<SubmitModel>

    //this to get All Events

    @GET("events")
    fun getSessions(@Header("Authorization") authHeader: String): Call<SessionsResponse>

    @GET("posts")
    fun getPosts(@Query("page") page: Int, @Header("Authorization") authHeader: String): Call<PostsModelResponse>

    @DELETE("posts/{post}")
    fun deletePost(@Path("post") postId: Int, @Header("Authorization") authHeader: String): Call<SubmitModel>

    @GET("notifications")
    fun getNotification(@Query("page") page: Int, @Header("Authorization") authHeader: String): Call<NotificationModelResponse>

    @GET("practices")
    fun getQuestions(@Header("Authorization") authHeader: String): Call<QuestionsModelResponse>

    @GET("practices/{practice}")
    fun getSingeQuestion(@Path("practice") practiceId: Int, @Header("Authorization") authHeader: String): Call<SingelQuestionModel>


    @Multipart
    @POST("posts")
    fun addPost(@Part("content") email: RequestBody, @Part image: MultipartBody.Part, @Header("Authorization") authHeader: String): Call<SubmitModel>

    @GET("setting")
    fun getLocation(@Header("Authorization") authHeader: String): Call<LocationResponse>

    @GET("articles")
    fun getArticles(): Call<ResponseBody>

    @GET("polls")
    fun getPolls(@Header("Authorization") authHeader: String): Call<PollModel>

    @GET("polls/{poll}")
    fun getSingePolls(@Path("poll") pollId: Int, @Header("Authorization") authHeader: String): Call<SingelPollModel>

    @POST("polls")
    fun submitPolls(@Body body: Map<String, String>, @Header("Authorization") authHeader: String): Call<SubmitModel>

    @POST("practices")
    fun submitAnswer(@Body body: Map<String, String>, @Header("Authorization") authHeader: String): Call<SubmitModel>

    @POST("practices/options/rating")
    fun submitRateAnswer(@Body body: Map<String, String>, @Header("Authorization") authHeader: String): Call<SubmitModel>

    @POST("sessions/{session}/rating")
    fun submitSessionsRate(@Path("session") session: Int, @Body body: Map<String, String>, @Header("Authorization") authHeader: String): Call<SubmitModel>

    @GET("speakers")
    fun getSpeakers(@Header("Authorization") authHeader: String): Call<SpeakersResponseModel>

    @GET("speakers")
    fun getSpeakersPaginated(@Query("page") page: Int, @Header("Authorization") authHeader: String): Call<SpeakersResponseModel>

    @GET("agenda/{day}")
    fun getAgenda(@Path("day") eventId: Int, @Header("Authorization") authHeader: String): Call<AgendaModelResponse>

    @GET("articles/{article}")
    fun getSingleArticle(@Path("article") eventId: Int): Call<ResponseBody>


    // get single event data
    @GET("events/{event}")
    fun getSingleEventData(@Path("event") eventId: Int, @Header("type") type: Int, @Header("Authorization") authHeader: String): Call<SingleEventResponse>


}