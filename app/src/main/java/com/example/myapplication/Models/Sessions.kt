package com.example.myapplication.Models

import com.example.myapplication.Models.EventModels.SpeakerSession
import com.google.gson.annotations.SerializedName

data class Sessions(

    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("location") val location: String,
    @SerializedName("time_from") val time_from: String,
    @SerializedName("time_to") val time_to: String,
    @SerializedName("speakers") val speakers: List<SpeakerSession>
)
