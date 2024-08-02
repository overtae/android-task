package com.example.androidtask.data.repository

import android.content.Context
import com.example.androidtask.R
import com.example.androidtask.presentation.ListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val BOOKMARK_LIST = "bookmark_list"

class BookmarkRepository(context: Context) {
    private val gson = Gson()
    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    private val _bookmarkList = loadBookmarkList()
    val bookmarkList get() = _bookmarkList.toList()

    fun addBookmark(item: ListItem) {
        if (!_bookmarkList.contains(item)) {
            _bookmarkList.add(0, item)
            saveBookmarkList()
        }
    }

    fun removeBookmark(item: ListItem) {
        _bookmarkList.remove(item)
        saveBookmarkList()
    }

    private fun loadBookmarkList(): MutableList<ListItem> {
        val jsonString = sharedPreferences.getString(BOOKMARK_LIST, null) ?: return mutableListOf()
        val listType = object : TypeToken<MutableList<ListItem>>() {}.type

        return gson.fromJson(jsonString, listType)
    }

    private fun saveBookmarkList() {
        val jsonString = gson.toJson(_bookmarkList)
        sharedPreferences.edit().putString(BOOKMARK_LIST, jsonString).apply()
    }
}