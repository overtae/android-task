package com.example.androidtask.data.repository

import com.example.androidtask.data.model.ImageResponse
import com.example.androidtask.data.model.VideoResponse

interface SearchRepository {
    suspend fun getImageList(searchText: String, page: Int): ImageResponse
    suspend fun getVideoList(searchText: String, page: Int): VideoResponse
}