package com.example.androidtask.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtask.data.repository.ImageRepositoryImpl
import com.example.androidtask.data.repository.VideoRepositoryImpl
import com.example.androidtask.presentation.ListItem
import com.example.androidtask.presentation.sortedByDatetime
import com.example.androidtask.presentation.toImageListItem
import com.example.androidtask.presentation.toVideoListItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "SearchViewModel"

class SearchViewModel(
    private val imageRepository: ImageRepositoryImpl = ImageRepositoryImpl(),
    private val videoRepository: VideoRepositoryImpl = VideoRepositoryImpl()
) : ViewModel() {
    private val _searchResult = MutableLiveData<List<ListItem>>()
    val searchResult: LiveData<List<ListItem>> = _searchResult

    fun fetchSearchResult(searchText: String) {
        viewModelScope.launch {
            runCatching {
                val imageResult =
                    imageRepository.getImageList(searchText).documents?.toImageListItem()
                        ?: listOf()
                val videoResult =
                    videoRepository.getVideoList(searchText).documents?.toVideoListItem()
                        ?: listOf()
                _searchResult.value = imageResult.plus(videoResult).sortedByDatetime()
            }.onFailure {
                Log.e(TAG, "fetchSearchResult() 실패 : ${it.message}")
                handleException(it)
            }
        }
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