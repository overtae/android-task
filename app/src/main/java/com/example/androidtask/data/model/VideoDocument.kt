package com.example.androidtask.data.model

import com.google.gson.annotations.SerializedName

data class VideoDocument(
    @SerializedName("author")
    val author: String?,
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("play_time")
    val playTime: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
)