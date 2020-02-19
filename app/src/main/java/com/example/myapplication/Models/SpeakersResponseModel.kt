package com.example.myapplication.Models

import com.example.myapplication.Models.EventModels.Links
import com.example.myapplication.Models.EventModels.Meta
import com.example.myapplication.Models.EventModels.Speakers
import com.google.gson.annotations.SerializedName


data class SpeakersResponseModel(
    @SerializedName("data") val data: List<Speakers>,
    @SerializedName("links") val links: Links,
    @SerializedName("meta") val meta: Meta
)