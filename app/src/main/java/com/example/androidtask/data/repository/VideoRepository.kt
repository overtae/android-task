package com.example.androidtask.data.repository

import com.example.androidtask.data.model.VideoResponse

interface VideoRepository {
    suspend fun getVideoList(searchText: String): VideoResponse
}