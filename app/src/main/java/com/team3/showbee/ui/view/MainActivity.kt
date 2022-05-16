package com.team3.showbee.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.ui.adapter.CalendarAdapter
import com.team3.showbee.R
import com.team3.showbee.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CalendarAdapter.OnMonthChangeListener {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    lateinit var calendarAdapter: CalendarAdapter
    private val baseCalendar = BaseCalendar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        calendarAdapter = CalendarAdapter(onMonthChangeListener = this)

        binding.fgCalRv.layoutManager = GridLayoutManager(this, BaseCalendar.DAYS_OF_WEEK)
        binding.fgCalRv.adapter = calendarAdapter

        binding.fgCalPre.setOnClickListener {
            calendarAdapter.changeToPrevMonth()
        }

        binding.fgCalNext.setOnClickListener {
            calendarAdapter.changeToNextMonth()
        }

        baseCalendar.initBaseCalendar {
            onMonthChanged(it)
        }
    }

    override fun onMonthChanged(calendar: Calendar) {
        val sdf = SimpleDateFormat("yyyy년 MM월", Locale.KOREAN)
        binding.fgCalMonth.text = sdf.format(calendar.time)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}