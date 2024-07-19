package com.example.androidtask.util

import android.icu.text.DecimalFormat

fun Int.formatNumber(): String {
    return when (this) {
        in 0..9999 -> DecimalFormat("#,###").format(this)
        else -> "${this / 1000}만"
    }
}