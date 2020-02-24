package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName


data class QuestionsModelResponse(
    @SerializedName("data") val data: List<QuestionModel>
)