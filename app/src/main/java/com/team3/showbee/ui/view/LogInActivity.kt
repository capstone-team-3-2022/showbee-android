package com.team3.showbee.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.team3.showbee.SharedPref
import com.team3.showbee.data.model.BaseResponse
import com.team3.showbee.data.model.Token
import com.team3.showbee.databinding.ActivityLogInBinding
import com.team3.showbee.data.repository.RequestRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInActivity : AppCompatActivity() {
    private var _binding: ActivityLogInBinding? = null
    private val binding: ActivityLogInBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.logInBtn.setOnClickListener {
            val email = binding.userId.text.toString()
            val password = binding.userPw.text.toString()

            signIn(email, password)
            Toast.makeText(this@LogInActivity, password, Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(email: String, password: String) {
        val signInRequest = RequestRepository().requestSignIn(email = email, password = password)

        signInRequest.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                if (response.code() == 200) {
                    try {
                        SharedPref.saveToken(Token(response.body()?.data!!))
                    } finally {
                        val intent = Intent(this@LogInActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.e("error", "${t.message}")
            }
        })
    }
}