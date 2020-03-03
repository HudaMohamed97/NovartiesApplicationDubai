package com.imagin.myapplication.Models

import com.google.gson.annotations.SerializedName

class PostData(
    @SerializedName("id") val id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("num_comments") val num_comments: Int,
    @SerializedName("owner") val owner: PostOwner,
    @SerializedName("created_at") val created_at: String

)