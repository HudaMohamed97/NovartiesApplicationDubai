package com.imagin.myapplication.Models

import com.google.gson.annotations.SerializedName


data class AgendaModelResponse(
    @SerializedName("day") val day: AgendaData,
    @SerializedName("num_of_days") val num_of_days: Int
)
