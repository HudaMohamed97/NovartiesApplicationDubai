package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class submitModel(
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String
)