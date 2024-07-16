package com.example.androidtask.data

import android.os.Parcelable
import com.example.androidtask.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Live(
    val viewer: Int,
    val title: String,
    val streamer: Streamer,
    val liveTime: String,
    val game: String = "",
    val screenImg: Int = R.drawable.img_live_1,
    val tags: List<String> = listOf("", "", ""),
    val supporters: List<Supporter> = listOf(),
) : Parcelable

@Parcelize
data class Supporter(
    val name: String,
    val cheese: Double,
) : Parcelable
