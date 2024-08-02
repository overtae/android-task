package com.example.androidtask.presentation

import com.example.androidtask.data.model.ImageDocument
import com.example.androidtask.data.model.VideoDocument

fun List<ImageDocument>.toImageListItem(): List<ListItem> {
    return this.map {
        ListItem.ImageItem(
            thumbnailUrl = it.thumbnailUrl ?: "",
            siteName = it.displaySitename ?: "",
            datetime = it.datetime ?: "",
            isBookmarked = false,
        )
    }
}

fun List<VideoDocument>.toVideoListItem(): List<ListItem> {
    return this.map {
        ListItem.VideoItem(
            thumbnail = it.thumbnail ?: "",
            title = it.title ?: "",
            datetime = it.datetime ?: "",
            isBookmarked = false,
        )
    }
}

fun List<ListItem>.sortedByDatetime() =
    this.sortedByDescending { if (it is ListItem.ImageItem) it.datetime else (it as ListItem.VideoItem).datetime }