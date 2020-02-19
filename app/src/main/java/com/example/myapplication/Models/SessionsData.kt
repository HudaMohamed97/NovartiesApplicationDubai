package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName


data class SessionsData(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("city") val city: String,
    @SerializedName("active") val active: Boolean
)
