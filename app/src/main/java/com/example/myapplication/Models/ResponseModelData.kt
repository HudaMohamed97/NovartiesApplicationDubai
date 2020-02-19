package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class ResponseModelData(
    @SerializedName("access_token") val access_token: String,
    @SerializedName("token_type") val token_type: String,
    @SerializedName("expires_in") val expires_in: String,
    @SerializedName("account") val account: Account
)

