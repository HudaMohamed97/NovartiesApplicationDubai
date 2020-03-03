package com.imagin.myapplication.Models

import com.google.gson.annotations.SerializedName


data class SingelQuestionModel (

    @SerializedName("data") val data : QuestionModel
)