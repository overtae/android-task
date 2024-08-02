package com.example.androidtask.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtask.data.repository.BookmarkRepository
import com.example.androidtask.data.viewmodel.BookmarkViewModel
import com.example.androidtask.databinding.ItemSearchResultBinding
import com.example.androidtask.util.toFormattedDatetime

class MainAdapter(val onClick: (ListItem) -> Unit) :
    ListAdapter<ListItem, MainAdapter.ResultViewHolder>(object :
        DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.thumbnailUrl == newItem.thumbnailUrl
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }
    ) {

    inner class ResultViewHolder(binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val thumbnailImageView = binding.ivThumbnail
        private val siteNameTextView = binding.tvSiteName
        private val datetimeTextView = binding.tvDatetime
        private val bookmarkToggleButton = binding.tbBookmark

        fun bind(item: ListItem) = with(item) {
            siteNameTextView.text = siteName
            datetimeTextView.text = datetime.toFormattedDatetime()
            bookmarkToggleButton.isChecked =
                BookmarkViewModel(BookmarkRepository(itemView.context)).isBookmarked(item)
            Glide.with(thumbnailImageView.context)
                .load(thumbnailUrl)
                .into(thumbnailImageView)
            itemView.setOnClickListener {
                bookmarkToggleButton.isChecked = !bookmarkToggleButton.isChecked
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}