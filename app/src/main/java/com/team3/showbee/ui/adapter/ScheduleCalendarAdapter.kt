package com.team3.showbee.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.R
import com.team3.showbee.databinding.CalItemBinding
import com.team3.showbee.databinding.IconItemBinding
import java.text.DecimalFormat
import java.util.*

class ScheduleCalendarAdapter(private val onMonthChangeListener: OnMonthChangeListener? = null, val context: Context) : RecyclerView.Adapter<ScheduleCalendarAdapter.CalendarItemViewHolder>() {

    private val baseCalendar = BaseCalendar()
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var dateMap: MutableMap<String, List<String>>

    private lateinit var sheduleCategoryAdapter: ScheduleCategoryAdapter

    init {
        baseCalendar.initBaseCalendar {
            onMonthChangeListener?.onMonthChanged(it)
        }
        dateMap = mutableMapOf()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IconItemBinding.inflate(layoutInflater, parent, false)
        return CalendarItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return BaseCalendar.LOW_OF_CALENDAR * BaseCalendar.DAYS_OF_WEEK
    }

    fun changeToPrevMonth() {
        baseCalendar.changeToPrevMonth {
            onMonthChangeListener?.onMonthChanged(it)
            notifyDataSetChanged()
        }
    }

    fun changeToNextMonth() {
        baseCalendar.changeToNextMonth {
            onMonthChangeListener?.onMonthChanged(it)
            notifyDataSetChanged()
        }
    }

    interface OnMonthChangeListener {
        fun onMonthChanged(calendar : Calendar)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setItems(item: Map<String, List<String>>) {
        dateMap.clear()
        dateMap.putAll(item)
    }

    inner class CalendarItemViewHolder(private val binding: IconItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Int, map: MutableMap<String, List<String>>, position: Int) {
            binding.tvDate.text = date.toString()

            for(i in map.keys) {
                if (i.toInt() == date) {
//                    binding.tvDate.text = map[i]?.get(0)
                    sheduleCategoryAdapter = map[i].let { ScheduleCategoryAdapter(map[i]) }
                    binding.categoryRecyclerview.adapter = sheduleCategoryAdapter
                    binding.categoryRecyclerview.layoutManager = GridLayoutManager(context, 2)
                }
            }

            when {
                position % BaseCalendar.DAYS_OF_WEEK == 0 -> binding.tvDate.setTextColor(Color.parseColor("#FF1E1E"))
                position % BaseCalendar.DAYS_OF_WEEK == 6 -> binding.tvDate.setTextColor(Color.parseColor("#2079FF"))
                else -> binding.tvDate.setTextColor(Color.parseColor("#676d6e"))
            }

            if (position < baseCalendar.preMonth
                || position >= baseCalendar.preMonth + baseCalendar.currentMonth) {
                binding.tvDate.alpha = 0.3f
                binding.tvDate.setTextColor(Color.parseColor("#8d93ab"))
            } else {
                binding.tvDate.alpha = 1f
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

    override fun onBindViewHolder(holder: CalendarItemViewHolder, position: Int) {
        if (holder is CalendarItemViewHolder) {
            holder.bind(baseCalendar.data[position], dateMap, position)
        }
    }
}