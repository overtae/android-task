package com.example.androidtask.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtask.data.repository.BookmarkRepository

class BookmarkViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> BookmarkViewModel(
                BookmarkRepository(context.applicationContext)
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}