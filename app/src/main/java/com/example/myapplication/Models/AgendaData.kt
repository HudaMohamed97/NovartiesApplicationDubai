package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class AgendaData (@SerializedName("id") val id : Int,
                       @SerializedName("date") val date : String,
                       @SerializedName("sessions") val sessions : List<Sessions>)
