package com.team3.showbee.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.data.entity.FinancialContentModel
import com.team3.showbee.databinding.CalItemBinding
import com.team3.showbee.databinding.CategoryItemBinding
import com.team3.showbee.ui.viewmodel.BaseCalendar
import java.text.DecimalFormat

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
            binding.category.text = list?.get(position)
        }
    }
}