package com.example.androidtask.holder

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Header

class HeaderHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(header: Header) {
        view.apply {
            val tvMore = view.findViewById<TextView>(R.id.tv_more)
            val tvHeader = view.findViewById<TextView>(R.id.tv_header)
            val builder = SpannableStringBuilder(header.text)

            if (!header.more) {
                tvMore.visibility = View.GONE
            }
            if (header.game.isNotEmpty()) {
                builder.setSpan(
                    ForegroundColorSpan(Color.parseColor("#00FFA3")),
                    0,
                    header.game.length,
                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            tvHeader.text = builder
        }
    }
}