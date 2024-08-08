package com.example.androidtask.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.androidtask.R
import com.example.androidtask.databinding.ItemLoadingBinding
import com.example.androidtask.databinding.ItemSearchResultBinding
import com.example.androidtask.presentation.data.ListItem
import com.example.androidtask.util.setMaxLineToggle
import com.example.androidtask.util.toFormattedDatetime

class SearchAdapter(private val onClick: (ListItem) -> Unit) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return when {
                oldItem is ListItem.ImageItem && newItem is ListItem.ImageItem -> oldItem.uuid == newItem.uuid
                oldItem is ListItem.VideoItem && newItem is ListItem.VideoItem -> oldItem.uuid == newItem.uuid
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
        const val TYPE_LOADING = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.ImageItem -> TYPE_IMAGE
            is ListItem.VideoItem -> TYPE_VIDEO
            else -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_IMAGE -> ImageViewHolder(
                ItemSearchResultBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onClick
            )

            TYPE_VIDEO -> VideoViewHolder(
                ItemSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onClick
            )

            else -> LoadingViewHolder(
                ItemLoadingBinding.inflate(
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
            is ListItem.VideoItem -> (holder as VideoViewHolder).bind(item)
            else -> return
        }
    }

    fun stopLoading() {
        val items = currentList.filter { it !is ListItem.LoadingItem }
        submitList(items)
    }

    class ImageViewHolder(
        binding: ItemSearchResultBinding,
        private val onClick: (ListItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val thumbnailImageView = binding.ivThumbnail
        private val siteNameTextView = binding.tvSiteName
        private val datetimeTextView = binding.tvDatetime
        private val bookmarkToggleButton = binding.tbBookmark

        fun bind(item: ListItem.ImageItem) = with(item) {
            val constraintSet = ConstraintSet()
            constraintSet.clone(thumbnailImageView.parent as androidx.constraintlayout.widget.ConstraintLayout)
            constraintSet.setDimensionRatio(thumbnailImageView.id, "1:1")
            constraintSet.applyTo(thumbnailImageView.parent as androidx.constraintlayout.widget.ConstraintLayout)
            Glide.with(thumbnailImageView.context)
                .load(thumbnailUrl)
                .centerCrop()
                .transform(RoundedCorners(50))
                .into(thumbnailImageView)

            siteNameTextView.setMaxLineToggle(
                itemView.context.getString(
                    R.string.search_item_image,
                    siteName
                ), 2
            )
            datetimeTextView.text = datetime.toFormattedDatetime()
            bookmarkToggleButton.isChecked = item.isBookmarked

            itemView.setOnClickListener {
                bookmarkToggleButton.isChecked = !bookmarkToggleButton.isChecked
                onClick(item)
            }
        }
    }

    class VideoViewHolder(
        binding: ItemSearchResultBinding,
        private val onClick: (ListItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val thumbnailImageView = binding.ivThumbnail
        private val siteNameTextView = binding.tvSiteName
        private val datetimeTextView = binding.tvDatetime
        private val bookmarkToggleButton = binding.tbBookmark

        fun bind(item: ListItem.VideoItem) = with(item) {
            val constraintSet = ConstraintSet()
            constraintSet.clone(thumbnailImageView.parent as androidx.constraintlayout.widget.ConstraintLayout)
            constraintSet.constrainHeight(thumbnailImageView.id, ConstraintSet.WRAP_CONTENT)
            constraintSet.applyTo(thumbnailImageView.parent as androidx.constraintlayout.widget.ConstraintLayout)
            Glide.with(thumbnailImageView.context)
                .load(thumbnail)
                .fitCenter()
                .transform(RoundedCorners(50))
                .into(thumbnailImageView)

            siteNameTextView.setMaxLineToggle(
                itemView.context.getString(
                    R.string.search_item_video,
                    title
                ), 2
            )
            datetimeTextView.text = datetime.toFormattedDatetime()
            bookmarkToggleButton.isChecked = item.isBookmarked

            itemView.setOnClickListener {
                bookmarkToggleButton.isChecked = !bookmarkToggleButton.isChecked
                onClick(item)
            }
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)
}