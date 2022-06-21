package com.team3.showbee.ui.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.Color
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.team3.showbee.R
import com.team3.showbee.data.entity.inviteeList
import com.team3.showbee.databinding.ActivityAddIncomeExpenditureBinding
import com.team3.showbee.databinding.ActivityMainBinding
import com.team3.showbee.ui.adapter.FinancialCalendarAdapter
import com.team3.showbee.ui.adapter.InviteeListAdapter
import com.team3.showbee.ui.adapter.ScheduleCalendarAdapter
import com.team3.showbee.ui.viewmodel.BaseCalendar
import com.team3.showbee.ui.viewmodel.ScheduleViewModel
import com.team3.showbee.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.log

@AndroidEntryPoint
class AddIncomeExpenditureActivity : AppCompatActivity() {
    private var _binding: ActivityAddIncomeExpenditureBinding? = null
    private val binding: ActivityAddIncomeExpenditureBinding get() = requireNotNull(_binding)
    private lateinit var viewModel: ScheduleViewModel

    lateinit var inviteeListAdapter: InviteeListAdapter

    var thisYear = ""
    var thisMonth = ""
    var thisDay = ""
    var category = true
    var resultDay = ""
    var cycle = 0
    var shared = false
    var mode = true
    var sid: Long = -1
    var inoutcome = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddIncomeExpenditureBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        setContentView(binding.root)

        inviteeListAdapter = InviteeListAdapter()

        binding.inviteeRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.inviteeRecyclerview.adapter = inviteeListAdapter

        mode = intent.getBooleanExtra("mode", true)
        sid = intent.getLongExtra("sid", -1)
        Log.d("mode", "onCreate: ${mode}")
        Log.d("sid", "onCreate: ${sid}")

        initView()
        observeData()
    }

    private fun initView() {
        checkMode()
        write()

    }

    private fun checkMode() {
        Log.d(TAG, "checkMode: @@@@@@@@@@@@@@@@@@@")
        if (!mode) {
            //조회, 수정
            Log.d(TAG, "checkMode: 여기는 수정모드")    
            binding.save.visibility = View.GONE

            Log.d(TAG, "checkMode: 여기는 데이터 부르기전")
            viewModel.getSchedule(sid)

            Log.d(TAG, "checkMode: 여기는 데이터 부르고 난 후")
            binding.delete.setOnClickListener {
                viewModel.deleteSchedule(sid)
            }
            binding.choiceIncomeExpense.setOnCheckedChangeListener{group, checkedId ->
                when (checkedId) {
                    R.id.radioButton -> {
                        binding.radioButton.setTextColor(Color.parseColor("#FF8B00"))
                        binding.radioButton2.setTextColor(Color.parseColor("#989898"))
                        inoutcome = true

                    }
                    R.id.radioButton2 -> {
                        binding.radioButton.setTextColor(Color.parseColor("#989898"))
                        binding.radioButton2.setTextColor(Color.parseColor("#FF8B00"))
                        inoutcome = false
                    }
                }
            }

            binding.update.setOnClickListener {
                Log.d(TAG, "checkMode: 들어왔나???")
                viewModel.updateSchedule(
                    sid = sid,
                    stitle = binding.editTextTextPersonName.text.toString(),
                    content = binding.memo.text.toString(),
                    price = binding.price.text.toString().toInt(),
                    date = binding.datePicker.text.toString(),
                    cycle = cycle,
                    shared = shared,
                    participant = inviteeListAdapter.getItem(),
                    inoutcome = category,
                    category = binding.selecCategory.text.toString()
                )
            }

        } else {
            Log.d(TAG, "checkMode: 여기는 글작성 모드")
            binding.delete.visibility = View.GONE
            binding.update.visibility = View.GONE

            binding.save.setOnClickListener {
                Log.d("글 등록 구현", "initView: ${shared}")
                isParticipant()
                Log.d("글 등록 구현", "initView: ${shared}")
                viewModel.createS(
                    stitle = binding.editTextTextPersonName.text.toString(),
                    content = binding.memo.text.toString(),
                    price = binding.price.text.toString().toInt(),
                    date = resultDay,
                    cycle = cycle,
                    shared = shared,
                    participant = inviteeListAdapter.getItem(),
                    inoutcome = category,
                    category = binding.selecCategory.text.toString()
                )
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
            }
        }
    }

    private fun write() {
        binding.choiceIncomeExpense.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton -> {
                    binding.radioButton.setTextColor(Color.parseColor("#FF8B00"))
                    binding.radioButton2.setTextColor(Color.parseColor("#989898"))
                    category = true
                }
                R.id.radioButton2 -> {
                    binding.radioButton.setTextColor(Color.parseColor("#989898"))
                    binding.radioButton2.setTextColor(Color.parseColor("#FF8B00"))
                    category = false
                }
            }
        }
        //날짜 선택
        binding.datePicker.setOnClickListener {
            setCalenderDay()
        }


        //반복주기 선택
        ArrayAdapter.createFromResource(
            this,
            R.array.cycle_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.cycleSpinner.adapter = adapter
        }

        binding.cycleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if (pos != 0) {
                    var selectedSpinner = binding.cycleSpinner.selectedItem.toString()
                    if (selectedSpinner == "매주") {
                        cycle = 7
                    } else if (selectedSpinner == "2주") {
                        cycle = 14
                    } else if (selectedSpinner == "한달") {
                        cycle = 1
                    }
                    Toast.makeText(
                        this@AddIncomeExpenditureActivity,
                        binding.cycleSpinner.selectedItem.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        //카테고리 선택
        binding.selecCategory.setOnClickListener {
            val moveToCategory = Intent(this, CategoryActivity::class.java)
            startActivity(moveToCategory)
        }
        val text = intent.getStringExtra("icon")
        Log.d(TAG, "write: ${text}")
        binding.selecCategory.text = text
        Log.d(TAG, "binding.selecCategory.text.toString: ${binding.selecCategory.text.toString()}")

        //사용자 초대
        binding.searchBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            viewModel.inviteUser(email = email)
            Log.d("성공했나?", "${email}")
            Log.d("텍스트띄우기", "onCreate: ${inviteeList.size}")
        }
        binding.addInviteeList.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            inviteeListAdapter.addItems(email)
            inviteeListAdapter.notifyDataSetChanged()
        }
    }

    private fun isParticipant() {
        if (inviteeListAdapter.itemCount() != 0) {
            shared = true
        }
    }

    private fun setCalenderDay() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateListener = object : DatePickerDialog.OnDateSetListener {
            @SuppressLint("SetTextI18n")
            override fun onDateSet(
                view: DatePicker?,
                yearDate: Int,
                monthDate: Int,
                dayOfMonth: Int
            ) {
                binding.datePicker.text = "${yearDate}년 ${monthDate + 1}월 ${dayOfMonth}일"
                thisMonth = "${monthDate + 1}"
                thisDay = "$dayOfMonth"

                if (thisMonth.length != 2) {
                    thisMonth = "0$thisMonth"
                }

                if (thisDay.length != 2) {
                    thisDay = "0$thisDay"
                }
                thisYear = "$yearDate"
                resultDay = "$thisYear-$thisMonth-$thisDay"
            }
        }

        val datePicker = DatePickerDialog(this, dateListener, year, month, day)
        datePicker.show()
    }

    private fun observeData() {
        with(viewModel) {
            email.observe(this@AddIncomeExpenditureActivity) {
                binding.inviteEmail.text = it
            }
            schedule.observe(this@AddIncomeExpenditureActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    binding.model = it

                    val priceInt = it.price.toString()
                    binding.price.setText(priceInt)

                    val category = it.category
                    binding.selecCategory.setText(category)

                    when (it.cycle) {
                        7 -> {
                            Log.d(TAG, "observeData: 7")
                            binding.cycleSpinner.setSelection(1)
                        }
                        14 -> {
                            Log.d(TAG, "observeData: 14")
                            binding.cycleSpinner.setSelection(2)
                        }
                        1 -> {
                            Log.d(TAG, "observeData: 1")
                            binding.cycleSpinner.setSelection(3)
                        }
                        else -> {
                            Log.d(TAG, "observeData: 0")
                            binding.cycleSpinner.setSelection(0)
                        }
                    }
                    /*
                    if(it.shared) {
                        for (i in 0 until it.participant.size) {
                            inviteeListAdapter.addItems(it.participant[i])
                            inviteeListAdapter.notifyDataSetChanged()
                        }
                    }

                     */

                    inoutcome = it.inoutcome
                    if (it.inoutcome) {
                        binding.choiceIncomeExpense.check(R.id.radioButton)
                        binding.radioButton.setTextColor(Color.parseColor("#FF8B00"))
                        binding.radioButton2.setTextColor(Color.parseColor("#989898"))
                    } else {
                        binding.choiceIncomeExpense.check(R.id.radioButton2)
                        binding.radioButton.setTextColor(Color.parseColor("#989898"))
                        binding.radioButton2.setTextColor(Color.parseColor("#FF8B00"))
                    }
                }
            }
        }
    }
}