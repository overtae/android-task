package com.example.androidtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Chat
import kotlin.random.Random

class ChatAdapter : ListAdapter<Chat, ChatAdapter.Holder>(object : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }

}) {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvUsername: TextView = view.findViewById(R.id.tv_username)
        private val tvContent: TextView = view.findViewById(R.id.tv_content)
        private val ivFan: ImageView = view.findViewById(R.id.iv_fan)

        fun bind(chat: Chat) {
            if (Random.nextBoolean()) ivFan.visibility = View.GONE
            tvUsername.setTextColor(getRandomTextColor())
            tvUsername.text = chat.username
            tvContent.text = chat.content
        }

        private fun getRandomTextColor(): Int {
            val colors = listOf(
                R.color.chat_0,
                R.color.chat_1,
                R.color.chat_2,
                R.color.chat_3,
                R.color.chat_4,
                R.color.chat_5
            )
            return ContextCompat.getColor(itemView.context, colors.random())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}