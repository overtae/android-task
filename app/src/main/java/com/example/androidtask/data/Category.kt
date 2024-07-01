package com.example.androidtask.data

data class Category(
    val name: String,
    val liveCount: Int,
    val viewer: Int,
    val img: Int,
    val isNew: Boolean = false
) {
}