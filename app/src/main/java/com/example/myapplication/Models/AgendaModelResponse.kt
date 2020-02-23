package com.example.myapplication.Models

import com.example.myapplication.Models.EventModels.Links
import com.example.myapplication.Models.EventModels.Meta
import com.google.gson.annotations.SerializedName


data class AgendaModelResponse(
    @SerializedName("day") val day: AgendaData,
    @SerializedName("num_of_days") val num_of_days: Int
)
