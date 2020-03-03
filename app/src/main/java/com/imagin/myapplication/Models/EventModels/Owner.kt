package com.imagin.myapplication.Models.EventModels

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("type") val type: String
)
