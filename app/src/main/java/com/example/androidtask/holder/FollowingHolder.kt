package com.example.androidtask.holder

import android.text.SpannableStringBuilder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Streamer

class FollowingHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(streamer: Streamer) {
        view.apply {
            val tvName = view.findViewById<TextView>(R.id.tv_streamer_name)
            val ivProfile = view.findViewById<ImageView>(R.id.iv_streamer_profile)

            ivProfile.setImageResource(streamer.profileImg)
            ivProfile.background =
                ResourcesCompat.getDrawable(resources, R.drawable.container_circle, null)
            ivProfile.clipToOutline = true

            val spannableString = SpannableStringBuilder("${streamer.name}  ")
            if (streamer.isVerified) {
                val verifiedMark =
                    ResourcesCompat.getDrawable(resources, R.drawable.img_verified_mark, null)
                spannableString.setSpan(
                    verifiedMark,
                    streamer.name.length - 1,
                    streamer.name.length,
                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            tvName.text = spannableString
        }
    }
}