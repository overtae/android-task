package com.example.androidtask.presentation.recent_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.databinding.ItemRecentSearchBinding

class RecentSearchAdapter(
    private val onDelete: (item: String) -> Unit,
    private val onClick: (item: String) -> Unit
) :
    ListAdapter<String, RecentSearchAdapter.Holder>(object :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }) {

    class Holder(
        binding: ItemRecentSearchBinding,
        private val onDelete: (item: String) -> Unit,
        private val onClick: (item: String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val recentSearchTextView = binding.tvRecentSearch
        private val deleteButton = binding.ivDelete

        fun bind(item: String) {
            recentSearchTextView.text = item
            deleteButton.setOnClickListener { onDelete(item) }
            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemRecentSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onDelete,
            onClick
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}