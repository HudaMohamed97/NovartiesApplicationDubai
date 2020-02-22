package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

class PostOwner(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("active") val active: Boolean = false,
    @SerializedName("type") val type: Int = 0,
    @SerializedName("bio") val bio: String = "",
    @SerializedName("photo") val photo: String = ""
)
