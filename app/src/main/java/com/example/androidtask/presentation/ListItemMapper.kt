package com.example.androidtask.presentation

import com.example.androidtask.data.remote.Document

fun List<Document>.toListItem(): List<ListItem> {
    return this.map {
        ListItem(
            thumbnailUrl = it.thumbnailUrl ?: "",
            siteName = it.displaySitename ?: "",
            datetime = it.datetime ?: ""
        )
    }
}
