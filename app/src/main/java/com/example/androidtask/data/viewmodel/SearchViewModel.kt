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

    fun fetchSearchResult(searchText: String, page: Int = 1) {
        viewModelScope.launch {
            runCatching {
                // TODO: 값을 가져오는 방식 다시 생각해보기
                //  * 현재 날짜 상관 없이 image 5개, video 5개를 불러오고 -> 날짜 순으로 정렬
                //  * 그러나 image 결과 값과 video 결과 값의 날짜 범위는 서로 다르다.
                //  * 따라서 매번 불러올 때마다 기존에 표시되던 리스트의 순서가 변경되는 일이 생긴다.
                //  * 어케함???
                val imageResponse = imageRepository.getImageList(searchText, page)
                val videoResponse = videoRepository.getVideoList(searchText, page)

                val imageResult = imageResponse.documents?.toImageListItem() ?: listOf()
                val videoResult = videoResponse.documents?.toVideoListItem() ?: listOf()

                val nextPageResult = imageResult.plus(videoResult)
                _searchResult.value =
                    if (page == 1) nextPageResult else _searchResult.value?.plus(nextPageResult)
                        ?.sortedByDatetime()
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