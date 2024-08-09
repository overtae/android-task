package com.example.androidtask.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources.getSystem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.example.androidtask.R
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

private const val PREF_RECENT_SEARCH = "recent_search"

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

fun TextView.setMaxLineToggle(originalText: String, maxLine: Int) {
    text = originalText
    post {
        if (layout.lineCount > maxLine - 1 && layout.getEllipsisCount(lineCount - 1) > 0) {
            this.setOnClickListener {
                maxLines = if (maxLines == maxLine) Int.MAX_VALUE else maxLine
            }
        }
    }
}

fun saveSearchHistory(context: Context, item: List<String>) {
    val gson = Gson()
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )
    val edit = sharedPref.edit()
    edit.putString(PREF_RECENT_SEARCH, gson.toJson(item))
    edit.apply()
}

fun loadSearchHistory(context: Context): List<String> {
    val gson = Gson()
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )
    val json = sharedPref.getString(PREF_RECENT_SEARCH, "")
    return gson.fromJson(json, Array<String>::class.java)?.toList() ?: listOf()
}