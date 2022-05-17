package com.team3.showbee.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.team3.showbee.R
import com.team3.showbee.SharedPref
import com.team3.showbee.databinding.ActivityLogInBinding
import com.team3.showbee.databinding.ActivitySignUpBinding
import com.team3.showbee.ui.viewmodel.LogInViewModel
import com.team3.showbee.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private var _binding: ActivitySignUpBinding? = null
    private val binding: ActivitySignUpBinding get() = requireNotNull(_binding)
    private lateinit var viewModel: UserViewModel

    var isEmailChecked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setContentView(binding.root)

        initView()
        observeData()
    }

    private fun initView() {
        val emailEt = findViewById<EditText>(R.id.userIdSignUp)
        val nameEt = findViewById<EditText>(R.id.userNickname)
        val pwEt = findViewById<EditText>(R.id.userPwSignUp)
        val pwChkEt = findViewById<EditText>(R.id.userPwCheck)
        val signupBtn = findViewById<Button>(R.id.signUp)
        val emailCheckBtn = findViewById<Button>(R.id.emailCheckBtn)

        emailCheckBtn.setOnClickListener {
            val userEmail = binding.userIdSignUp.text.toString()

            viewModel.checkEmail(userEmail)
        }

        signupBtn.setOnClickListener {
            val userEmail = emailEt.text.toString()
            val userName = nameEt.text.toString()
            val userPw = pwEt.text.toString()
            val userPwCheck = pwChkEt.text.toString()

            if (userPw == userPwCheck) {
                viewModel.signup(userEmail, userName, userPw)
            }
        }
    }

    private fun observeData() {
        with(viewModel) {
            msg.observe(this@SignUpActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    Toast.makeText(this@SignUpActivity, it, Toast.LENGTH_SHORT).show()
                    if (it == "성공하였습니다.") {
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            emailCheck.observe(this@SignUpActivity) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it) {
                        Toast.makeText(this@SignUpActivity, "이미 사용중인 이메일입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@SignUpActivity, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show()
                        isEmailChecked = true
                    }
                }
            }
        }
    }
}