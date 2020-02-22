package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

class PostOwner(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("type") val type: Int,
    @SerializedName("bio") val bio: String,
    @SerializedName("photo") val photo: String
)
