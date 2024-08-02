package com.example.androidtask.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources.getSystem
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

val Float.px get() = (this * getSystem().displayMetrics.density).toInt()
val Int.dp get() = (this / getSystem().displayMetrics.density).toInt()

fun String.toFormattedDatetime(): String {
    // 2024-08-01T12:13:15.000+09:00
    val formatterBuilder = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        .toFormatter()
    val localDateTime = LocalDateTime.parse(this, formatterBuilder) ?: LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return localDateTime.format(formatter)
}

fun Context.hideKeyBoard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

