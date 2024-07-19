package com.example.androidtask.data

object User {
    val followingList: ArrayList<Live> = arrayListOf()

    fun follow(live: Live) {
        followingList.add(live)
    }

    fun unFollow(live: Live) {
        followingList.remove(live)
    }

    fun isFollowed(live: Live) = followingList.contains(live)
}