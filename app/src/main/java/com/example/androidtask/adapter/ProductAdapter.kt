package com.example.androidtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.data.Product
import com.example.androidtask.databinding.ItemProductBinding
import java.text.DecimalFormat

class ProductAdapter(val items: List<Product>) : RecyclerView.Adapter<ProductAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.image.setImageResource(items[position].image)
        holder.name.text = items[position].name
        holder.address.text = items[position].address
        holder.price.text = items[position].price.toFormattedString() + "원"
        holder.chat.text = items[position].chat.toString()
        holder.like.text = items[position].likes.toString()
    }

    private fun Int.toFormattedString(): String {
        return DecimalFormat("#,###").format(this)
    }

    inner class Holder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivProduct
        val name = binding.tvName
        val address = binding.tvAddress
        val price = binding.tvPrice
        val chat = binding.tvChat
        val like = binding.tvLike
    }
}