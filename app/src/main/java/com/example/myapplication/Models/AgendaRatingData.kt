package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class AgendaRatingData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: Int,
    @SerializedName("options") val options: List<OptionModel>,
    @SerializedName("created_at") val created_at: String
)


