package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class OptionModel(
    @SerializedName("id") val id: Int,
    @SerializedName("option") val option: String,
    @SerializedName("created_at") val created_at: String
)