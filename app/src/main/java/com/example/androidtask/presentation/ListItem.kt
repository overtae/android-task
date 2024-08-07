package com.example.androidtask.presentation

sealed class ListItem {
    data class ImageItem(
        val uuid: String,
        val thumbnailUrl: String,
        val siteName: String,
        val datetime: String,
        val isBookmarked: Boolean = false
    ) : ListItem()

    data class VideoItem(
        val uuid: String,
        val thumbnail: String,
        val title: String,
        val datetime: String,
        val isBookmarked: Boolean = false
    ) : ListItem()

    data class LoadingItem(
        val isLoading: Boolean
    ) : ListItem()
}