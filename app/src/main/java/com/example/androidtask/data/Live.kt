package com.example.androidtask.data

import com.example.androidtask.R

data class Live(
    val viewer: Int,
    val title: String,
    val streamer: String,
) {
    var game: String = ""
    var screenImg: Int? = R.drawable.img_live_01
    var profileImg: Int? = R.drawable.img_anonymous
    var tags: List<String>? = listOf("", "", "")

    // LgLive
    constructor(
        viewer: Int,
        title: String,
        streamer: String,
        game: String,
    ) : this(
        viewer,
        title,
        streamer,
    ) {
        this.game = game
    }

    // SmLive
    constructor(
        viewer: Int,
        title: String,
        streamer: String,
        tags: ArrayList<String>
    ) : this(
        viewer,
        title,
        streamer,
    ) {
        this.tags = tags
    }

    // LgLive with Image
    constructor(
        viewer: Int,
        title: String,
        streamer: String,
        game: String,
        screenImg: Int,
        profileImg: Int
    ) : this(
        viewer,
        title,
        streamer,
    ) {
        this.game = game
        this.screenImg = screenImg
        this.profileImg = profileImg
    }

    // SmLive with Image
    constructor(
        viewer: Int,
        title: String,
        streamer: String,
        screenImg: Int,
        profileImg: Int,
        tags: ArrayList<String>
    ) : this(
        viewer,
        title,
        streamer,
    ) {
        this.screenImg = screenImg
        this.profileImg = profileImg
        this.tags = tags
    }

    override fun toString(): String {
        return super.toString()
    }
}