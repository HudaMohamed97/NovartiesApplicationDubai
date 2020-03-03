package com.imagin.myapplication.Models

import com.imagin.myapplication.Models.EventModels.QuestionModelOption
import com.google.gson.annotations.SerializedName

data class QuestionModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("options") val options: List<QuestionModelOption>,
    @SerializedName("created_at") val created_at: String
)