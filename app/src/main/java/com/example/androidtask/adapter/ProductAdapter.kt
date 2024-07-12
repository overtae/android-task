package com.example.androidtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Product
import com.example.androidtask.databinding.ItemProductBinding

class ProductAdapter(private val context: Context) :
    ListAdapter<Product, ProductAdapter.Holder>(object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }) {
    interface ItemClickListener {
        fun onClick(item: Product)
        fun onLongClick(item: Product)
    }

    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.ivProduct
        private val name = binding.tvName
        private val address = binding.tvAddress
        private val price = binding.tvPrice
        private val chat = binding.tvChat
        private val likeCount = binding.tvLike
        private val likeIcon = binding.ivLike

        fun bind(item: Product) {
            itemView.setOnClickListener { itemClickListener?.onClick(item) }
            itemView.setOnLongClickListener {
                itemClickListener?.onLongClick(item)
                true
            }
            image.setImageResource(item.image)
            name.text = item.name
            address.text = item.address
            price.text = context.getString(R.string.price, item.price)
            chat.text = item.chat.toString()
            likeCount.text = item.likes.toString()
            likeIcon.isChecked = item.isLiked
        }
    }
}