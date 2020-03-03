package com.imagin.myapplication.Models.EventModels

import com.google.gson.annotations.SerializedName

data class QuestionModelOption(
    @SerializedName("id") val id: Int,
    @SerializedName("option") val title: String,
    @SerializedName("created_at") val created_at: String

)