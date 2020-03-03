package com.imagin.myapplication.Models

import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("company_name") val company_name: String,
    @SerializedName("event_name") val event_name: String,
    @SerializedName("address") val address: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)

