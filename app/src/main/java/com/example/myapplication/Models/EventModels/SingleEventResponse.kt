package com.example.myapplication.Models.EventModels

import com.google.gson.annotations.SerializedName


data class SingleEventResponse(

    @SerializedName("data") val data: SingleEventData
)