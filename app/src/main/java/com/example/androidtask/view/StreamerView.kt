package com.example.androidtask.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.androidtask.R

class StreamerView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private var ivProfile: ImageView
    private var tvName: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.streamer_view, this, false)
        addView(view)

        tvName = findViewById(R.id.tv_streamer_name)
        ivProfile = findViewById(R.id.iv_streamer_profile)
    }

    fun setProfile(imgRes: Int) {
        ivProfile.setImageResource(imgRes)
        ivProfile.background =
            ResourcesCompat.getDrawable(resources, R.drawable.container_circle, null)
        ivProfile.clipToOutline = true
    }

    fun setName(text: String) {
        tvName.text = text
    }
}