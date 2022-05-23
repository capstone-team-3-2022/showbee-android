package com.team3.showbee.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team3.showbee.R
import com.team3.showbee.databinding.FragmentFinancialBinding
import com.team3.showbee.databinding.FragmentListBinding
import com.team3.showbee.ui.adapter.FinancialCalendarAdapter
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.ui.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

class ListFragment : Fragment(), FinancialCalendarAdapter.OnMonthChangeListener {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = requireNotNull(_binding)
    private val viewModel by activityViewModels<UserViewModel>()

    lateinit var financialCalendarAdapter: FinancialCalendarAdapter
    private val baseCalendar = BaseCalendar()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater)
        observeData()
        initView()
        return binding.root
    }

    fun initView() {
        financialCalendarAdapter = FinancialCalendarAdapter(onMonthChangeListener = this)
        binding.listRv.adapter = financialCalendarAdapter

        binding.fgCalPre.setOnClickListener {
            financialCalendarAdapter.changeToPrevMonth()
        }

        binding.fgCalNext.setOnClickListener {
            financialCalendarAdapter.changeToNextMonth()
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
        }
    }

    override fun onMonthChanged(calendar: Calendar) {
        val sdf = SimpleDateFormat("yyyy년 MM월", Locale.KOREAN)
        binding.fgCalMonth.text = sdf.format(calendar.time)
    }
}