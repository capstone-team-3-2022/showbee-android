package com.team3.showbee.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.data.model.Schedule
import com.team3.showbee.R
import java.util.*

class CalendarAdapter(private val scheduleList: List<Schedule>? = null, private val onMonthChangeListener: OnMonthChangeListener? = null) : RecyclerView.Adapter<CalendarAdapter.CalendarItemViewHolder>() {

    private val baseCalendar = BaseCalendar()

    init {
        baseCalendar.initBaseCalendar {
            onMonthChangeListener?.onMonthChanged(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cal_item, parent, false)
        return CalendarItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return BaseCalendar.LOW_OF_CALENDAR * BaseCalendar.DAYS_OF_WEEK
    }

    override fun onBindViewHolder(holder: CalendarItemViewHolder, position: Int) {
        val tvDate: TextView = holder.itemView.findViewById(R.id.tv_date)
        val academicSchedule: TextView = holder.itemView.findViewById(R.id.academicSchedule)

        if (position % BaseCalendar.DAYS_OF_WEEK == 0) tvDate.setTextColor(Color.parseColor("#FF1E1E"))
        else if(position % BaseCalendar.DAYS_OF_WEEK == 6) tvDate.setTextColor(Color.parseColor("#2079FF"))
        else tvDate.setTextColor(Color.parseColor("#676d6e"))

        val day = baseCalendar.data[position]

        tvDate.text = baseCalendar.data[position].toString()
        academicSchedule.text = scheduleList?.find { schedule -> schedule.day == day }?.schedule ?: ""

        if(academicSchedule.text != "") {
            tvDate.setBackgroundColor(Color.parseColor("#2c786c"))
            tvDate.setTextColor(Color.parseColor("#FFFFFF"))
        }

        if(scheduleList?.find { it.day == day }?.holiday==""){
            tvDate.setBackgroundColor(Color.parseColor("#fddb3a"))
            tvDate.setTextColor(Color.parseColor("#393b44"))
        }

        if (position < baseCalendar.preMonth
            || position >= baseCalendar.preMonth + baseCalendar.currentMonth) {
            tvDate.alpha = 0.3f
            tvDate.setTextColor(Color.parseColor("#8d93ab"))
            academicSchedule.setText("")
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


    interface OnMonthChangeListener {
        fun onMonthChanged(calendar : Calendar)
    }

    class CalendarItemViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)
}