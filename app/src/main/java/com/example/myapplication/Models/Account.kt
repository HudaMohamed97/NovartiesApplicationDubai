package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName


data class Account(

    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "name",
    @SerializedName("email") val email: String = "email",
    @SerializedName("active") val active: Boolean = false,
    @SerializedName("type") val type: Int = 1,
    @SerializedName("bio") val bio: String = "bio",
    @SerializedName("photo") val photo: String = ""
)