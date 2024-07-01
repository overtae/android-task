package com.example.androidtask.holder

import android.icu.text.DecimalFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Live

class LgLiveHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(live: Live) {
        view.apply {
            val tvViewer = view.findViewById<TextView>(R.id.tv_lg_live_viewer)
            val tvTitle = view.findViewById<TextView>(R.id.tv_lg_live_title)
            val tvStreamer = view.findViewById<TextView>(R.id.tv_lg_live_streamer)
            val tvGame = view.findViewById<TextView>(R.id.tv_lg_live_game)

            val ivScreen = view.findViewById<ImageView>(R.id.iv_lg_live_screen)
            val ivProfile = view.findViewById<ImageView>(R.id.iv_lg_live_profile)

            tvViewer.text = DecimalFormat("#,###").format(live.viewer)
            tvTitle.text = live.title
            tvStreamer.text = live.streamer
            tvGame.text = live.game

            ivScreen.setImageResource(live.screenImg!!)
            ivProfile.setImageResource(live.profileImg!!)
        }
    }
}