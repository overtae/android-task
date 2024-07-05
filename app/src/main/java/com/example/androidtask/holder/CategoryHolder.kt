package com.example.androidtask.holder

import android.icu.text.DecimalFormat
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Category

class CategoryHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(category: Category) {
        view.apply {
            val tvName = view.findViewById<TextView>(R.id.tv_ct_name)
            val tvLiveCount = view.findViewById<TextView>(R.id.tv_ct_live_count)
            val tvViewer = view.findViewById<TextView>(R.id.tv_ct_viewer)

            val ivScreen = view.findViewById<ImageView>(R.id.iv_ct_screen)

            val builder = SpannableStringBuilder(category.name)

            if (category.isNew) {
                builder.append("  ")
                builder.setSpan(
                    ImageSpan(context, R.drawable.ic_new),
                    category.name.length + 1,
                    category.name.length + 2,
                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            tvName.text = builder
            tvViewer.text = formatNumber(category.viewer)
            tvLiveCount.text = view.context.getString(R.string.live_count, category.liveCount)

            ivScreen.setImageResource(category.img)
        }
    }

    private fun formatNumber(number: Int): String {
        return when (number) {
            in 0..9999 -> DecimalFormat("#,###").format(number)
            else -> "${number / 1000}만"
        }
    }
}