package com.imagin.myapplication.Models

import com.google.gson.annotations.SerializedName

data class SingePollData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("options") val options: List<OptionModel>,
    @SerializedName("created_at") val created_at: String
)
