package com.example.androidtask.data.repository

import com.example.androidtask.data.remote.ImageResponse

interface ImageRepository {
    suspend fun getImageList(searchText: String): ImageResponse
}