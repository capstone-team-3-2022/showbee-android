package com.team3.showbee.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.data.entity.FinancialContentModel
import com.team3.showbee.data.entity.ScheduleContentModel
import com.team3.showbee.databinding.ListStitleItemBinding
import com.team3.showbee.databinding.ListTitleItemBinding
import com.team3.showbee.ui.view.AddIncomeExpenditureActivity
import com.team3.showbee.ui.view.UpdateFinancialActivity

class ScheduleDayListAdapter(val context: Context): RecyclerView.Adapter<ScheduleDayListAdapter.Holder>() {

    private var itemList: MutableMap<String, MutableList<ScheduleContentModel>> = mutableMapOf()
    private lateinit var itemClickListener : ScheduleDayListAdapter.OnItemClickListener

    private lateinit var scheduleContentAdapter: ScheduleContentAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDayListAdapter.Holder {
        val binding = ListStitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleDayListAdapter.Holder, position: Int) {
        holder.bind(itemList, position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ListStitleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MutableMap<String, MutableList<ScheduleContentModel>>, position: Int) {
            Log.d("item", "$item")
            val list = item.keys.toList()
            val content = item[list[position]]

            binding.textView11.text = list[position]
            scheduleContentAdapter = content?.let { ScheduleContentAdapter(it) }!!
            binding.recyclerviewScheduleList.adapter = scheduleContentAdapter
            binding.recyclerviewScheduleList.layoutManager = LinearLayoutManager(context)

            scheduleContentAdapter.setItemClickListener(object : ScheduleContentAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    Log.d("schedule", "contentadapter: ${content[position].sid}")
                    //수정할것
                    val intent = Intent(context, AddIncomeExpenditureActivity::class.java)
                        .putExtra("sid", content[position].sid)
                        .putExtra("mode", false)
                    context.startActivity(intent)
                }
            })

            val pos = absoluteAdapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    itemClickListener.onClick(itemView,pos)
                }
            }
        }
    }
    fun setItems(item: MutableMap<String, MutableList<ScheduleContentModel>>) {
        itemList.clear()
        itemList.putAll(item)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: ScheduleDayListAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
}