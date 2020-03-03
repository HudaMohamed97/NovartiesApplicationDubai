package com.imagin.myapplication.Models.EventModels

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String,
    @SerializedName("prev") val prev: Object,
    @SerializedName("next") val next: Object
)
