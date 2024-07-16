package com.example.androidtask.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    val username: String,
    val content: String,
) : Parcelable