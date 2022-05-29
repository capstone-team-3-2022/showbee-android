package com.team3.showbee.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.data.entity.Financial
import com.team3.showbee.data.entity.FinancialContentModel
import com.team3.showbee.databinding.ListContentItemBinding
import java.text.DecimalFormat

class FinancialContentAdapter(private val itemList: MutableList<FinancialContentModel>): RecyclerView.Adapter<FinancialContentAdapter.Holder>() {

    private lateinit var itemClickListener : FinancialContentAdapter.OnItemClickListener

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
            val dec = DecimalFormat("#,###원")
            binding.textView13.text = dec.format(item.price.toInt())
            binding.textView12.text = item.content
            binding.textView14.text = item.category

            if (item.inoutcome) {
                binding.textView13.setTextColor(Color.parseColor("#2079FF"))
            }
            else {
                binding.textView13.setTextColor(Color.parseColor("#FF1E1E"))
            }

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

    fun setItemClickListener(onItemClickListener: FinancialContentAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
}
