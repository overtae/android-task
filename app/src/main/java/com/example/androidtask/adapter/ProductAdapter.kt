package com.example.androidtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.Product
import com.example.androidtask.databinding.ItemProductBinding

class ProductAdapter(private val context: Context, private val items: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.Holder>() {
    interface ItemClickListener {
        fun onClick(view: View, item: Product)
    }

    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
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
            itemView.setOnClickListener { itemClickListener?.onClick(it, item) }
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