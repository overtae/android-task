package com.example.androidtask.holder

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.view.StreamerView
import com.example.androidtask.data.Streamer


class StreamerHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(streamer: List<Streamer>) {
        view.apply {
            val layout = findViewById<LinearLayout>(R.id.linear_streamer)

            streamer.map { s ->
                val streamerView = StreamerView(view.context).apply {
                    setProfile(s.profileImg)
                    setName(s.name)
                }
                layout.addView(streamerView)
            }
        }
    }
}