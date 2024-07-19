package com.example.androidtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Header
import com.example.androidtask.data.ListDataType
import com.example.androidtask.data.ListDataWrapper
import com.example.androidtask.data.Live
import com.example.androidtask.holder.CommonLiveHolder
import com.example.androidtask.holder.HeaderHolder

class MainAdapter(val onClick: (live: Live) -> Unit) :
    ListAdapter<ListDataWrapper, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<ListDataWrapper>() {
        override fun areItemsTheSame(oldItem: ListDataWrapper, newItem: ListDataWrapper): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(
            oldItem: ListDataWrapper,
            newItem: ListDataWrapper
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.sm_live_item -> {
                CommonLiveHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.sm_live_item, parent, false)
                )
            }

            R.layout.header_item -> {
                HeaderHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
                )
            }

            else -> {
                Holder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.sub_recycler_item, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.sm_live_item -> {
                val data = getItem(position).data as Live
                (holder as CommonLiveHolder).bindData(data)
                holder.itemView.setOnClickListener { onClick(data) }
            }

            R.layout.header_item -> {
                val data = getItem(position).data as Header
                (holder as HeaderHolder).bindData(data)
            }

            R.layout.sub_recycler_item -> {
                val parseList = (getItem(position).data as List<*>).map {
                    ListDataWrapper(getItem(position).type, it as Any)
                }
                val rvSub = (holder as Holder).rvSub
                rvSub.adapter = SubAdapter(parseList, onClick)
                rvSub.layoutManager = LinearLayoutManager(
                    holder.itemView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            ListDataType.TYPE_SMALL_LIVE -> R.layout.sm_live_item
            ListDataType.TYPE_HEADER -> R.layout.header_item
            else -> R.layout.sub_recycler_item
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val rvSub: RecyclerView = view.findViewById(R.id.rv_sub)
    }
}