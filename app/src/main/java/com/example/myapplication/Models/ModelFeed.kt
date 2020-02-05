package com.example.myapplication.Models

/**
 * this for Post Data
 */

data class ModelFeed(
    var id: Int,
    var likes: Int,
    var comments: Int,
    var proPic: Int,
    var postpic: Int,
    var name: String,
    var time: String,
    var status: String
)
