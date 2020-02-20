package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class PollData(

    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("created_at") val created_at: String
)