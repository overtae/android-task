package com.example.androidtask.presentation

import com.example.androidtask.data.model.ImageDocument
import com.example.androidtask.data.model.VideoDocument
import java.util.UUID

val ListItem.isBookmarked: Boolean get() = if (this is ListItem.ImageItem) isBookmarked else if (this is ListItem.VideoItem) isBookmarked else false

fun List<ImageDocument>.toImageListItem(): List<ListItem> {
    return this.map {
        ListItem.ImageItem(
            uuid = UUID.randomUUID().toString(),
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
            uuid = UUID.randomUUID().toString(),
            thumbnail = it.thumbnail ?: "",
            title = it.title ?: "",
            datetime = it.datetime ?: "",
            isBookmarked = false,
        )
    }
}

fun List<ListItem>.sortedByDatetime() =
    this.sortedByDescending { if (it is ListItem.ImageItem) it.datetime else (it as ListItem.VideoItem).datetime }

fun ListItem.copy(isBookmarked: Boolean): ListItem {
    return when (this) {
        is ListItem.ImageItem -> this.copy(isBookmarked = isBookmarked)
        is ListItem.VideoItem -> this.copy(isBookmarked = isBookmarked)
        else -> this
    }
}

fun List<ListItem>.find(item: ListItem) = when (item) {
    is ListItem.ImageItem -> this.find { it is ListItem.ImageItem && it.uuid == item.uuid }
    is ListItem.VideoItem -> this.find { it is ListItem.VideoItem && it.uuid == item.uuid }
    else -> null
}