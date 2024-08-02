package com.example.androidtask.data.repository

import com.example.androidtask.data.remote.ImageResponse
import com.example.androidtask.network.NetworkClient

class ImageRepositoryImpl : ImageRepository {
    override suspend fun getImageList(searchText: String): ImageResponse {
        return NetworkClient.kakaoNetwork.getImageList(search = searchText)
    }
}