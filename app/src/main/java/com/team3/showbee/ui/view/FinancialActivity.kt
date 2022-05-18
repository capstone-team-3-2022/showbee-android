package com.team3.showbee.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.ui.adapter.CalendarAdapter
import com.team3.showbee.R
import com.team3.showbee.SharedPref
import com.team3.showbee.data.entity.Token
import com.team3.showbee.databinding.ActivityMainBinding
import com.team3.showbee.ui.viewmodel.LogInViewModel
import com.team3.showbee.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FinancialActivity : AppCompatActivity(), CalendarAdapter.OnMonthChangeListener {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)
    private lateinit var viewModel: UserViewModel

    lateinit var calendarAdapter: CalendarAdapter
    private val baseCalendar = BaseCalendar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel =ViewModelProvider(this).get(UserViewModel::class.java)
        setContentView(binding.root)

        initView()
        observeData()
    }

    private fun initView() {
        binding.mainNavigationView.setNavigationItemSelectedListener {
            Log.d("Info", "navigaion item click... ${it.title}")
            when(it.title) {
                "나의 계정" -> {
                    val intent = Intent(this, UserAccountActivity::class.java)
                    startActivity(intent)
                }
                "로그아웃" -> {
                    val dialog = LogOutDialog()
                    dialog.setButtonClickListener(object : LogOutDialog.OnButtonClickListener {
                        override fun onLogOutOkClicked() {
                            SharedPref.saveToken(Token(""))
                            val intent = Intent(this@FinancialActivity, LogInActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    })
                    dialog.show(supportFragmentManager, "CustomDialog")
                }
                "회원탈퇴" -> {
                    val dialog = UserLeaveDialog()
                    dialog.setButtonClickListener(object : UserLeaveDialog.OnButtonClickListener {
                        override fun onLeaveOkClicked() {
                            viewModel.deleteUser()
                        }
                    })
                    dialog.show(supportFragmentManager, "CustomDialog")
                }
            }
            true
        }
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

        binding.btnAddExpenseAndIncome.setOnClickListener {
            val intent = Intent(this, AddFinancialActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionButton2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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

    private fun observeData() {
        with(viewModel) {
            msg.observe(this@FinancialActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    Toast.makeText(this@FinancialActivity, it, Toast.LENGTH_SHORT).show()
                    if (it=="성공하였습니다.") {
                        val intent = Intent(this@FinancialActivity, LogInActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }

            token.observe(this@FinancialActivity) {
                SharedPref.saveToken(it)
            }
        }
    }
}