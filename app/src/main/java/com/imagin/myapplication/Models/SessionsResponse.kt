package com.imagin.myapplication.Models

import com.imagin.myapplication.Models.EventModels.Links
import com.imagin.myapplication.Models.EventModels.Meta
import com.google.gson.annotations.SerializedName

data class SessionsResponse(
    @SerializedName("data") val data: List<SessionsData>,
    @SerializedName("links") val links: Links,
    @SerializedName("meta") val meta: Meta
)
