package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("data") val data: LocationModel
)