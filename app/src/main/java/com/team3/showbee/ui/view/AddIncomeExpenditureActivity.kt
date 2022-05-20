package com.team3.showbee.ui.view

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.team3.showbee.R
import com.team3.showbee.databinding.ActivityAddIncomeExpenditureBinding
import com.team3.showbee.databinding.ActivityMainBinding
import com.team3.showbee.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddIncomeExpenditureActivity : AppCompatActivity() {
    private var _binding: ActivityAddIncomeExpenditureBinding? = null
    private val binding: ActivityAddIncomeExpenditureBinding get() = requireNotNull(_binding)
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddIncomeExpenditureBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        var category = ""
        binding.choiceIncomeExpense.setOnCheckedChangeListener{group, checkedId ->
            when (checkedId) {
                R.id.radioButton -> {
                    binding.radioButton.setTextColor(Color.parseColor("#FF8B00"))
                    binding.radioButton2.setTextColor(Color.parseColor("#989898"))
                    category = "income"

                }
                R.id.radioButton2 -> {
                    binding.radioButton.setTextColor(Color.parseColor("#989898"))
                    binding.radioButton2.setTextColor(Color.parseColor("#FF8B00"))
                    category = "expense"
                }
            }
        }
        //날짜 선택
        var dateString = ""
        binding.datePicker.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                binding.datePicker.setText(dateString)
                Toast.makeText( this@AddIncomeExpenditureActivity,"날짜/시간 :  ${dateString}", Toast.LENGTH_SHORT).show()
                Log.d("date", "${month+1}, ${dayOfMonth}")
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(
                Calendar.DAY_OF_MONTH)).show()
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
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                if(pos !=0 ) {
                    Toast.makeText(this@AddIncomeExpenditureActivity, binding.cycleSpinner.selectedItem.toString(), Toast.LENGTH_SHORT).show()
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
        binding.selecCategory.text = text

        binding.imageView2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}