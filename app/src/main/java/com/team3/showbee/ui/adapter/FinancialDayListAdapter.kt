package com.team3.showbee.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.data.entity.FinancialContentModel
import com.team3.showbee.data.entity.FinancialListModel
import com.team3.showbee.databinding.ListTitleItemBinding
import com.team3.showbee.ui.viewmodel.BaseCalendar

class FinancialDayListAdapter(val context: Context): RecyclerView.Adapter<FinancialDayListAdapter.Holder>() {
    private var itemList: MutableMap<String, MutableList<FinancialContentModel>> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListTitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList, position)
//        holder.bind(itemList.list[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ListTitleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MutableMap<String, MutableList<FinancialContentModel>>, position: Int) {
            Log.d("item", "$item")
            val list = item.keys.toList()

            binding.textView11.text = list[position]
            binding.recyclerview.adapter = item[list[position]]?.let { FinancialContentAdapter(it) }
            binding.recyclerview.layoutManager = LinearLayoutManager(context)
        }
    }

    fun setItems(item: MutableMap<String, MutableList<FinancialContentModel>>) {
        itemList.putAll(item)
    }
}