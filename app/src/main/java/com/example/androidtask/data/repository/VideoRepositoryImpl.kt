package com.example.androidtask.data.repository

import com.example.androidtask.data.model.VideoResponse
import com.example.androidtask.network.NetworkClient

class VideoRepositoryImpl : VideoRepository {
    override suspend fun getVideoList(searchText: String, page: Int): VideoResponse {
        return NetworkClient.kakaoNetwork.getVideoList(search = searchText, page = page)
    }
}