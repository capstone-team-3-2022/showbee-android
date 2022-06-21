package com.team3.showbee.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.team3.showbee.R
import com.team3.showbee.databinding.FragmentScheduleListBinding
import com.team3.showbee.ui.adapter.FinancialCalendarAdapter
import com.team3.showbee.ui.adapter.FinancialDayListAdapter
import com.team3.showbee.ui.adapter.ScheduleCalendarAdapter
import com.team3.showbee.ui.adapter.ScheduleDayListAdapter
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.ui.viewmodel.ScheduleViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ScheduleListFragment : Fragment(), ScheduleCalendarAdapter.OnMonthChangeListener {
    private var _binding: FragmentScheduleListBinding?=null
    private val binding: FragmentScheduleListBinding get() = requireNotNull(_binding)
    private val viewModel by activityViewModels<ScheduleViewModel>()

    private lateinit var scheduleCalendarAdapter: ScheduleCalendarAdapter
    private lateinit var scheduleDayListAdapter: ScheduleDayListAdapter
    private val baseCalendar = BaseCalendar()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleListBinding.inflate(layoutInflater)
        observeData()
        initView()
        return binding.root
    }

    fun initView() {
        scheduleCalendarAdapter = ScheduleCalendarAdapter(onMonthChangeListener = this, context = requireContext() )
        scheduleDayListAdapter = ScheduleDayListAdapter(requireContext())
        binding.listRv.layoutManager = LinearLayoutManager(context)
        binding.listRv.adapter = scheduleDayListAdapter

        baseCalendar.initBaseCalendar {
            onMonthChanged(it)
        }

        scheduleDayListAdapter.setItemClickListener(object : ScheduleDayListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                activity?.let {
                    Log.d("schedule", "adapter: ${position}")
                }
            }
        })

        binding.fgCalPre.setOnClickListener {
            scheduleCalendarAdapter.changeToPrevMonth()
        }

        binding.fgCalNext.setOnClickListener {
            scheduleCalendarAdapter.changeToNextMonth()
        }
    }

    private fun observeData() {
        with(viewModel) {
            msg.observe(viewLifecycleOwner) {event ->
                event.getContentIfNotHandled()?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    if (it=="성공하였습니다.") {
                        val intent = Intent(context, LogInActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
            list.observe(viewLifecycleOwner) {event ->
                event.getContentIfNotHandled()?.let {
                    scheduleDayListAdapter.setItems(it)
                    scheduleDayListAdapter.notifyDataSetChanged()
                }
            }
            total.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    val dec = DecimalFormat("#,###원")
                    binding.incomeContent.text = dec.format(it[0])
                    binding.expenseContent.text = dec.format(it[1])
                }
            }
        }

    }

    override fun onMonthChanged(calendar: Calendar) {
        val sdf = SimpleDateFormat("yyyy년 MM월", Locale.KOREAN)
        val sdf2 = SimpleDateFormat("yyyy-MM", Locale.KOREAN)
        binding.fgCalMonth.text = sdf.format(calendar.time)
        viewModel.getSMonthlyTotal(sdf2.format(calendar.time))
        Log.d("financial", "onMonthChanged")
        viewModel.getSList(sdf2.format(calendar.time))
    }

}