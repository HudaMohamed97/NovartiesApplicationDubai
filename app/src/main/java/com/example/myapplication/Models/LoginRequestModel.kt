package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("type") val type: String
)

