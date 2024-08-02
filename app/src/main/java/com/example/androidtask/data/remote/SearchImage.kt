package com.example.androidtask.data.remote

import com.example.androidtask.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchImage {
    @GET("image")
    suspend fun getImageList(
        @Query("query") search: String, // 검색을 원하는 질의어
        @Query("sort") sort: String = "recency", // 결과 문서 정렬 방식 (recency: 최신순, accuracy (default): 정확도순)
        @Query("page") page: Int = 1, // 결과 페이지 번호, 1~15 사이
        @Query("size") size: Int = 80 // 한 페이지에 보여질 문서 수, 1~30 사이, 기본값 15
    ): ImageResponse
}