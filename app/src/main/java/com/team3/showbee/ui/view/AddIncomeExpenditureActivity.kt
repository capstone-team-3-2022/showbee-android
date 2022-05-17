package com.team3.showbee.ui.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}