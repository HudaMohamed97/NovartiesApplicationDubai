package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("message") val message: String,
    @SerializedName("type") val type: String,
    @SerializedName("created_at") val created_at: String
)