package com.team3.showbee.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.data.entity.FinancialContentModel
import com.team3.showbee.data.entity.ScheduleContentModel
import com.team3.showbee.databinding.ListContentItemBinding
import com.team3.showbee.databinding.ListScontentItemBinding
import java.text.DecimalFormat

class ScheduleContentAdapter(private val itemList: MutableList<ScheduleContentModel>): RecyclerView.Adapter<ScheduleContentAdapter.Holder>() {

    private lateinit var itemClickListener: ScheduleContentAdapter.OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleContentAdapter.Holder {
        val binding = ListScontentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(var binding: ListScontentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleContentModel) {
            val dec = DecimalFormat("#,###원")
            binding.textView13.text = dec.format(item.price.toInt())
            binding.textView12.text = item.price
            binding.textView14.text = item.stitle


            val pos = absoluteAdapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    itemClickListener.onClick(itemView,pos)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: ScheduleContentAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
}
