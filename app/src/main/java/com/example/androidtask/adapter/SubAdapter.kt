package com.example.androidtask.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Category
import com.example.androidtask.data.ListDataType
import com.example.androidtask.data.ListDataWrapper
import com.example.androidtask.data.Live
import com.example.androidtask.data.Streamer
import com.example.androidtask.holder.CategoryHolder
import com.example.androidtask.holder.CommonLiveHolder
import com.example.androidtask.holder.LgLiveHolder
import com.example.androidtask.holder.StreamerHolder

class SubAdapter(private val item: List<ListDataWrapper>, val onClick: (live: Live) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.lg_live_item -> {
                LgLiveHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.lg_live_item, parent, false)
                )
            }

            R.layout.md_live_item -> {
                CommonLiveHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.md_live_item, parent, false)
                )
            }

            R.layout.streamer_list_item -> {
                StreamerHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.streamer_list_item, parent, false)
                )
            }

            else -> {
                CategoryHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.category_item, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.lg_live_item -> {
                val data = item[position].data as Live
                (holder as LgLiveHolder).bindData(data)
                holder.itemView.setOnClickListener { onClick(data) }
            }

            R.layout.md_live_item -> {
                val data = item[position].data as Live
                (holder as CommonLiveHolder).bindData(data)
                holder.itemView.setOnClickListener { onClick(data) }
            }

            R.layout.category_item -> {
                val data = item[position].data as Category
                (holder as CategoryHolder).bindData(data)
            }

            R.layout.streamer_list_item -> {
                val data = item[position].data as List<*>
                val streamer = data.map {
                    it as Streamer
                }

                (holder as StreamerHolder).bindData(streamer)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (item[position].type) {
            ListDataType.TYPE_LARGE_LIVE -> R.layout.lg_live_item
            ListDataType.TYPE_MEDIUM_LIVE -> R.layout.md_live_item
            ListDataType.TYPE_STREAMER -> R.layout.streamer_list_item
            else -> R.layout.category_item
        }
    }
}