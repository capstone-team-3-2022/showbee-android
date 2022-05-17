package com.team3.showbee.ui.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}