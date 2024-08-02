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
    ListAdapter<ListItem, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return when {
                oldItem is ListItem.ImageItem && newItem is ListItem.ImageItem -> oldItem.thumbnailUrl == newItem.thumbnailUrl
                oldItem is ListItem.VideoItem && newItem is ListItem.VideoItem -> oldItem.thumbnail == newItem.thumbnail
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }
    ) {

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_VIDEO = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.ImageItem -> TYPE_IMAGE
            is ListItem.VideoItem -> TYPE_VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_IMAGE -> ImageViewHolder(
                ItemSearchResultBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> VideoViewHolder(
                ItemSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ListItem.ImageItem -> (holder as ImageViewHolder).bind(item)
            else -> (holder as VideoViewHolder).bind(item as ListItem.VideoItem)
        }
    }

    inner class ImageViewHolder(binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val thumbnailImageView = binding.ivThumbnail
        private val siteNameTextView = binding.tvSiteName
        private val datetimeTextView = binding.tvDatetime
        private val bookmarkToggleButton = binding.tbBookmark

        fun bind(item: ListItem.ImageItem) = with(item) {
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

    inner class VideoViewHolder(binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val thumbnailImageView = binding.ivThumbnail
        private val siteNameTextView = binding.tvSiteName
        private val datetimeTextView = binding.tvDatetime
        private val bookmarkToggleButton = binding.tbBookmark

        fun bind(item: ListItem.VideoItem) = with(item) {
            siteNameTextView.text = title
            datetimeTextView.text = datetime.toFormattedDatetime()
            bookmarkToggleButton.isChecked =
                BookmarkViewModel(BookmarkRepository(itemView.context)).isBookmarked(item)
            Glide.with(thumbnailImageView.context)
                .load(thumbnail)
                .into(thumbnailImageView)
            itemView.setOnClickListener {
                bookmarkToggleButton.isChecked = !bookmarkToggleButton.isChecked
                onClick(item)
            }
        }
    }
}