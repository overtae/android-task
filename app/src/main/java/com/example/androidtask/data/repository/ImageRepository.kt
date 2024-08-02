package com.example.androidtask.data.repository

import com.example.androidtask.data.model.ImageResponse

interface ImageRepository {
    suspend fun getImageList(searchText: String, page: Int): ImageResponse
}