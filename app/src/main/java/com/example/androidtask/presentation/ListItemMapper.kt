package com.example.androidtask.presentation

import com.example.androidtask.data.model.ImageDocument

fun List<ImageDocument>.toListItem(): List<ListItem> {
    return this.map {
        ListItem(
            thumbnailUrl = it.thumbnailUrl ?: "",
            siteName = it.displaySitename ?: "",
            datetime = it.datetime ?: "",
            isBookmarked = false,
        )
    }
}
