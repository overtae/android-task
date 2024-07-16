package com.example.androidtask.data

import android.os.Parcelable
import com.example.androidtask.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Streamer(
    val name: String,
    val profileImg: Int = R.drawable.img_anonymous,
    val isVerified: Boolean = false,
) : Parcelable