package com.team3.showbee.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.team3.showbee.R
import com.team3.showbee.databinding.ActivitySharedScheduleBinding
import com.team3.showbee.ui.adapter.FinancialCalendarAdapter
import com.team3.showbee.ui.adapter.FinancialDayListAdapter
import com.team3.showbee.ui.adapter.ScheduleSharedAdapter
import com.team3.showbee.ui.viewmodel.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedScheduleActivity : AppCompatActivity() {
    private var _binding: ActivitySharedScheduleBinding? = null
    private val binding: ActivitySharedScheduleBinding get() = requireNotNull(_binding)
    private lateinit var viewModel: ScheduleViewModel

    private lateinit var scheduleSharedAdapter: ScheduleSharedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySharedScheduleBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        setContentView(binding.root)

        initView()
        observeData()
    }

    private fun initView() {
        scheduleSharedAdapter = ScheduleSharedAdapter()
        binding.sharedRv.layoutManager = LinearLayoutManager(this)
        binding.sharedRv.adapter = scheduleSharedAdapter

        viewModel.getShared()

    }

    private fun observeData() {
        with(viewModel) {
            msg.observe(this@SharedScheduleActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    Toast.makeText(this@SharedScheduleActivity, "성공했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            shared.observe(this@SharedScheduleActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    scheduleSharedAdapter.addItems(it)
                    scheduleSharedAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}