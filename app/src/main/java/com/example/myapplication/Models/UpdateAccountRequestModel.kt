package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName
import java.io.File

data class UpdateAccountRequestModel(
    @SerializedName("type") val type: Int,
    @SerializedName("name") val name: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("enable_questions") val enable_questions: Int,
    @SerializedName("photo") val photo: File

)

