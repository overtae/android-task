package com.example.androidtask.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtask.presentation.ListItem
import com.example.androidtask.data.repository.ImageRepository
import com.example.androidtask.data.repository.ImageRepositoryImpl
import com.example.androidtask.presentation.toListItem
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

private const val TAG = "SearchViewModel"

class ImageViewModel(private val repository: ImageRepository = ImageRepositoryImpl()) :
    ViewModel() {

    private val _searchResult = MutableLiveData<List<ListItem>>()
    val searchResult: LiveData<List<ListItem>> = _searchResult

    fun fetchSearchResult(searchText: String) {
        viewModelScope.launch {
            runCatching {
                val result =
                    repository.getImageList(searchText).documents?.toListItem() ?: listOf()
                _searchResult.value = result
            }.onFailure {
                Log.e(TAG, "fetchSearchResult() 실패 : ${it.message}")
                handleException(it)
            }
        }
    }

    fun updateBookmarkState(list: List<ListItem>) {
        _searchResult.value = list
    }

    private fun handleException(e: Throwable) {
        when (e) {
            is HttpException -> {
                val errorJsonString = e.response()?.errorBody()?.string()
                Log.e(TAG, "HTTP error: $errorJsonString")
            }

            is IOException -> Log.e(TAG, "Network error: $e")
            else -> Log.e(TAG, "Unexpected error: $e")
        }
    }
}