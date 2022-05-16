package com.team3.showbee.ui.view

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.team3.showbee.R
import java.util.*

class AddIncomeExpenditureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income_expenditure)

        initView()
    }

    private fun initView() {

        val selectCategory = findViewById<EditText>(R.id.selectCategory)
        val datePicker = findViewById<EditText>(R.id.datePicker)
        val spinner: Spinner = findViewById(R.id.cycleSpinner)
        var dateString = ""

        //날짜 선택
        datePicker.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                datePicker.setText(dateString)
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
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                if(pos !=0 ) {
                    Toast.makeText(this@AddIncomeExpenditureActivity, spinner.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        //카테고리 선택
        selectCategory.setOnClickListener {

        }
    }
}