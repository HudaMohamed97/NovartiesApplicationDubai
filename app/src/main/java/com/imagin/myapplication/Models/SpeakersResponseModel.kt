package com.imagin.myapplication.Models

import com.imagin.myapplication.Models.EventModels.Links
import com.imagin.myapplication.Models.EventModels.Meta
import com.imagin.myapplication.Models.EventModels.Speakers
import com.google.gson.annotations.SerializedName


data class SpeakersResponseModel(
    @SerializedName("data") val data: List<Speakers>,
    @SerializedName("links") val links: Links,
    @SerializedName("meta") val meta: Meta
)