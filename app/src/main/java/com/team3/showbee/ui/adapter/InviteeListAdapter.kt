package com.team3.showbee.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.databinding.ItemInviteeListBinding

class InviteeListAdapter(): RecyclerView.Adapter<InviteeListAdapter.Holder>() {
    private var itemList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemInviteeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])
//        holder.bind(itemList.list[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ItemInviteeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Log.d("item", "$item")
            binding.itemInvitee.text = item
        }
    }

    fun addItems(item: String) {
        itemList.add(item)
    }
}