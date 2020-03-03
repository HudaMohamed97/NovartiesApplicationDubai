package com.imagin.myapplication.Models.EventModels

import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("data") val data: List<EventData>,
    @SerializedName("links") val links: Links,
    @SerializedName("meta") val meta: Meta
)
