package com.example.myapplication.Models.EventModels

import com.google.gson.annotations.SerializedName


data class SpeakerSession(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("type") val type: String,
    @SerializedName("active") val active: Boolean
)