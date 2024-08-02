package com.example.androidtask.presentation

sealed class ListItem {
    data class ImageItem(
        val thumbnailUrl: String,
        val siteName: String,
        val datetime: String,
        var isBookmarked: Boolean = false
    ) : ListItem()

    data class VideoItem(
        val thumbnail: String,
        val title: String,
        val datetime: String,
        var isBookmarked: Boolean = false
    ) : ListItem()

    data class LoadingItem(
        var isLoading: Boolean
    ) : ListItem()
}