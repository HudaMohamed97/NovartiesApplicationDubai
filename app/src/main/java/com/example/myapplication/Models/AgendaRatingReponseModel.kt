package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class AgendaRatingReponseModeldata(

    @SerializedName("data") val data: List<AgendaRatingData>
)