package com.team3.showbee.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.databinding.CategoryItemBinding
import com.bumptech.glide.Glide
import com.team3.showbee.R

class ScheduleCategoryAdapter(private val itemList: List<String>?): RecyclerView.Adapter<ScheduleCategoryAdapter.CategoryItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
        return CategoryItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList?.count() ?: 0
    }


    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        if (holder is CategoryItemViewHolder) {
            holder.bind(itemList, position)
        }
    }

    inner class CategoryItemViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list: List<String>?, position: Int) {
            when(list?.get(position)) {
                "카카오tv" -> Glide.with(itemView.context).load(R.drawable.kakao_tv).into(binding.imageView)
                "쿠팡" -> Glide.with(itemView.context).load(R.drawable.coupang).into(binding.imageView)
                "KT" -> Glide.with(itemView.context).load(R.drawable.kt).into(binding.imageView)
                "LG" -> Glide.with(itemView.context).load(R.drawable.lg).into(binding.imageView)
            }
//            binding.category.text = list?.get(position)
//            Glide.with(itemView.context).load(R.drawable.audio_book).into(binding.imageView)
        }
    }
}