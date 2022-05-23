package com.team3.showbee.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.R
import com.team3.showbee.databinding.CalItemBinding
import java.util.*

class FinancialCalendarAdapter(private val onMonthChangeListener: OnMonthChangeListener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val baseCalendar = BaseCalendar()
    private lateinit var itemClickListener : OnItemClickListener
    private lateinit var dateMap: MutableMap<String, List<Long>>

    init {
        baseCalendar.initBaseCalendar {
            onMonthChangeListener?.onMonthChanged(it)
        }

        dateMap = mutableMapOf("22" to listOf(2000, 1203))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CalItemBinding.inflate(layoutInflater, parent, false)
        return CalendarItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return BaseCalendar.LOW_OF_CALENDAR * BaseCalendar.DAYS_OF_WEEK
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CalendarItemViewHolder) {
            holder.bind(baseCalendar.data[position], dateMap)
        }
        val tvDate: TextView = holder.itemView.findViewById(R.id.tv_date)

        when {
            position % BaseCalendar.DAYS_OF_WEEK == 0 -> tvDate.setTextColor(Color.parseColor("#FF1E1E"))
            position % BaseCalendar.DAYS_OF_WEEK == 6 -> tvDate.setTextColor(Color.parseColor("#2079FF"))
            else -> tvDate.setTextColor(Color.parseColor("#676d6e"))
        }

        if (position < baseCalendar.preMonth
            || position >= baseCalendar.preMonth + baseCalendar.currentMonth) {
            tvDate.alpha = 0.3f
            tvDate.setTextColor(Color.parseColor("#8d93ab"))
        } else {
            tvDate.alpha = 1f
        }
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


    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }


    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }


    interface OnMonthChangeListener {
        fun onMonthChanged(calendar : Calendar)
    }

    inner class CalendarItemViewHolder(private val binding: CalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Int, map: Map<String, List<Long>>) {
            binding.tvDate.text = date.toString()
            binding.income.text = map["2022-05-17T00:00:00.000+00:00"]?.get(0)?.toString()
            binding.expense.text = map["2022-05-17T00:00:00.000+00:00"]?.get(1)?.toString()
            Log.d("financial", "CalendarItemViewHolder")

            val pos = absoluteAdapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    itemClickListener.onClick(itemView,pos)
                }
            }
        }
    }

    fun setItems(item: Map<String, List<Long>>) {
        Log.d("financial", "setItem")
        dateMap.putAll(item)
    }
}