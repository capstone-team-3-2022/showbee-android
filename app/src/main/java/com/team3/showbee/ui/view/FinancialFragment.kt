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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.team3.showbee.R
import com.team3.showbee.databinding.FragmentFinancialBinding
import com.team3.showbee.ui.adapter.FinancialCalendarAdapter
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.ui.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

class FinancialFragment : Fragment(), FinancialCalendarAdapter.OnMonthChangeListener {
    private var _binding: FragmentFinancialBinding? = null
    private val binding: FragmentFinancialBinding get() = requireNotNull(_binding)
    private val viewModel by activityViewModels<UserViewModel>()

    private lateinit var financialCalendarAdapter: FinancialCalendarAdapter
    private val baseCalendar = BaseCalendar()
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
        Log.d("financial", "onAttach: aaa")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinancialBinding.inflate(layoutInflater)
        observeData()
        initView()
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity = null
    }

    fun initView() {
        financialCalendarAdapter = FinancialCalendarAdapter(onMonthChangeListener = this)

        binding.fgCalRv.layoutManager = GridLayoutManager(context, BaseCalendar.DAYS_OF_WEEK)
        binding.fgCalRv.adapter = financialCalendarAdapter

        Log.d("financial", "why")

        binding.calendar.setOnClickListener {
            Log.d("financial", "why")
        }

        binding.fgCalRv.setOnClickListener {
            mainActivity?.choiceFragment("list")
            Log.d("financial", "listener: click")
        }
        financialCalendarAdapter.setItemClickListener(object : FinancialCalendarAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                activity?.let {
                    Log.d("financial", "adapter: click")
                }
            }
        })

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