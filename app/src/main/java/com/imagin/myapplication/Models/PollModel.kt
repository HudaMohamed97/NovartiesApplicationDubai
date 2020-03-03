package com.imagin.myapplication.Models

import com.imagin.myapplication.Models.EventModels.Links
import com.imagin.myapplication.Models.EventModels.Meta
import com.google.gson.annotations.SerializedName

data class PollModel(@SerializedName("data") val data : List<PollData>,
                     @SerializedName("links") val links : Links,
                     @SerializedName("meta") val meta : Meta
)