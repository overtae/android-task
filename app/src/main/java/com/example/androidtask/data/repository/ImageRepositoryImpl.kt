package com.example.androidtask.data.repository

import com.example.androidtask.data.model.ImageResponse
import com.example.androidtask.network.NetworkClient

class ImageRepositoryImpl : ImageRepository {
    override suspend fun getImageList(searchText: String, page: Int): ImageResponse {
        return NetworkClient.kakaoNetwork.getImageList(search = searchText, page = page)
    }
}