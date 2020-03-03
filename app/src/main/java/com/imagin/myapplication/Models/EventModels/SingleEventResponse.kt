package com.imagin.myapplication.Models.EventModels

import com.google.gson.annotations.SerializedName


data class SingleEventResponse(

    @SerializedName("data") val data: SingleEventData
)