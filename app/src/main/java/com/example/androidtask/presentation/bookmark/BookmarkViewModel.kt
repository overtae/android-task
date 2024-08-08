package com.example.androidtask.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtask.presentation.data.ListItem
import com.example.androidtask.data.repository.BookmarkRepository

class BookmarkViewModel(private val repository: BookmarkRepository) : ViewModel() {
    private val _bookmarkList: MutableLiveData<List<ListItem>> = MutableLiveData()
    val bookmarkList: LiveData<List<ListItem>> get() = _bookmarkList

    init {
        loadBookmarkList()
    }

    private fun loadBookmarkList() {
        _bookmarkList.value = repository.bookmarkList
    }

    fun addBookmark(item: ListItem) {
        repository.addBookmark(item)
        loadBookmarkList()
    }

    fun removeBookmark(item: ListItem) {
        repository.removeBookmark(item)
        loadBookmarkList()
    }
}
