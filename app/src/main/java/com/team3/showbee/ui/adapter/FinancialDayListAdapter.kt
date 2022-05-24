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
        holder.bind(itemList)
//        holder.bind(itemList.list[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ListTitleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MutableMap<String, MutableList<FinancialContentModel>>) {
            Log.d("item", "$item")
            for(i in item.keys) { //for문이 문제인듯 함 마지막 값이 들어감
                val list = item[i]
                binding.textView11.text = i
                binding.recyclerview.adapter = list?.let { FinancialContentAdapter(it) }
                binding.recyclerview.layoutManager = LinearLayoutManager(context)
            }
        }
    }

    fun setItems(item: MutableMap<String, MutableList<FinancialContentModel>>) {
        itemList.putAll(item)
    }
}