package com.team3.showbee.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.data.entity.Financial
import com.team3.showbee.data.entity.FinancialContentModel
import com.team3.showbee.databinding.ListContentItemBinding

class FinancialContentAdapter(private val itemList: MutableList<FinancialContentModel>): RecyclerView.Adapter<FinancialContentAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ListContentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FinancialContentModel) {
            binding.model = item
        }
    }

}
