package com.team3.showbee.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.data.entity.SharedContentModel
import com.team3.showbee.databinding.ItemInviteeListBinding
import com.team3.showbee.databinding.ListScontentItemBinding
import com.team3.showbee.databinding.ListSharedItemBinding

class ScheduleSharedAdapter: RecyclerView.Adapter<ScheduleSharedAdapter.Holder>() {
    private var itemList: MutableList<SharedContentModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListSharedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ListSharedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SharedContentModel) {
            Log.d("item", "$item")
            binding.sharedDate.text = item.date
            binding.sharedCategory.text = item.category
            binding.sharedCycle.text = changeText(item)
            binding.sharedPrice.text = item.price.toString()
        }
    }
    fun changeText(item: SharedContentModel): String {
        if(item.cycle == 1) {
            return "한달"
        } else if (item.cycle == 7) {
            return "매 주"
        } else if (item.cycle == 14) {
            return "2주"
        }
        return ""
    }
    fun addItems(item: List<SharedContentModel>) {
        itemList.clear()
        itemList.addAll(item)
    }
}