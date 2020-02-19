package com.example.myapplication.Models

import com.example.myapplication.Models.EventModels.Links
import com.example.myapplication.Models.EventModels.Meta
import com.google.gson.annotations.SerializedName

data class SessionsResponse(
    @SerializedName("data") val data: List<SessionsData>,
    @SerializedName("links") val links: Links,
    @SerializedName("meta") val meta: Meta
)
