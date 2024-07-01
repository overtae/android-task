package com.example.androidtask.holder

import android.icu.text.DecimalFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Live

class MdLiveHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(live: Live) {
        view.apply {
            val tvViewer = view.findViewById<TextView>(R.id.tv_md_live_viewer)
            val tvTitle = view.findViewById<TextView>(R.id.tv_md_live_title)
            val tvStreamer = view.findViewById<TextView>(R.id.tv_md_live_streamer)

            val ivScreen = view.findViewById<ImageView>(R.id.iv_md_live_screen)
            val ivProfile = view.findViewById<ImageView>(R.id.iv_md_live_profile)

            val tvTags = listOf<TextView>(
                view.findViewById(R.id.tv_md_live_tag_1),
                view.findViewById(R.id.tv_md_live_tag_2),
                view.findViewById(R.id.tv_md_live_tag_3),
            )

            tvViewer.text = DecimalFormat("#,###").format(live.viewer)
            tvTitle.text = live.title
            tvStreamer.text = live.streamer

            ivScreen.setImageResource(live.screenImg!!)
            ivProfile.setImageResource(live.profileImg!!)

            tvTags.mapIndexed { i, tvTag ->
                if (live.tags!![i] == "") tvTag.visibility = View.GONE
                tvTag.text = live.tags!![i]
            }
        }
    }
}