package com.team3.showbee.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.team3.showbee.databinding.FragmentScheduleBinding
import com.team3.showbee.ui.adapter.ScheduleCalendarAdapter
import com.team3.showbee.ui.adapter.ScheduleCategoryAdapter
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.ui.viewmodel.ScheduleViewModel
import com.team3.showbee.ui.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFragment : Fragment(), ScheduleCalendarAdapter.OnMonthChangeListener {
    private var _binding: FragmentScheduleBinding? = null
    private val binding: FragmentScheduleBinding get() = requireNotNull(_binding)
    private val viewModel by activityViewModels<ScheduleViewModel>()

    lateinit var scheduleCalendarAdapter: ScheduleCalendarAdapter
    private val baseCalendar = BaseCalendar()
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
        Log.d("schedule", "onAttach: vvv")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(layoutInflater)
//        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        observeData()
        initView()
        return binding.root
    }
    override fun onDetach() {
        super.onDetach()
        mainActivity = null
    }

    fun initView() {
        scheduleCalendarAdapter = ScheduleCalendarAdapter(onMonthChangeListener = this, requireContext())

        binding.fgCalRv.layoutManager = GridLayoutManager(context, BaseCalendar.DAYS_OF_WEEK)
        binding.fgCalRv.adapter = scheduleCalendarAdapter

        binding.fgCalPre.setOnClickListener {
            scheduleCalendarAdapter.changeToPrevMonth()
        }

        binding.fgCalNext.setOnClickListener {
            scheduleCalendarAdapter.changeToNextMonth()
        }

        baseCalendar.initBaseCalendar {
            onMonthChanged(it)
        }

    }

    private fun observeData() {
        with(viewModel) {
            msg.observe(viewLifecycleOwner) { event ->
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
            category.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    scheduleCalendarAdapter.setItems(it)
                    scheduleCalendarAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onMonthChanged(calendar: Calendar) {
        val sdf = SimpleDateFormat("yyyy년 MM월", Locale.KOREAN)
        val sdf2 = SimpleDateFormat("yyyy-MM", Locale.KOREAN)
        binding.fgCalMonth.text = sdf.format(calendar.time)
        viewModel.getCategory(sdf2.format(calendar.time))
    }
}