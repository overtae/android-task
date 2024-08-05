package com.example.androidtask.data.repository

import com.example.androidtask.data.model.ImageResponse
import com.example.androidtask.data.model.VideoResponse
import com.example.androidtask.data.remote.SearchMedia

class SearchRepositoryImpl(private val networkClient: SearchMedia) : SearchRepository {
    override suspend fun getImageList(searchText: String, page: Int): ImageResponse {
        return networkClient.getImageList(search = searchText, page = page)
    }

    override suspend fun getVideoList(searchText: String, page: Int): VideoResponse {
        return networkClient.getVideoList(search = searchText, page = page)
    }
}