package com.team3.showbee.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.team3.showbee.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val emailEt = findViewById<EditText>(R.id.userIdSignUp)
        val nameEt = findViewById<EditText>(R.id.userNickname)
        val pwEt = findViewById<EditText>(R.id.userPwSignUp)
        val pwChkEt = findViewById<EditText>(R.id.userPwCheck)
        val signupBtn = findViewById<Button>(R.id.signUp)
        val emailCheckBtn = findViewById<Button>(R.id.emailCheckBtn)

        val txt = "회원정보를 입력해주세요"
        val pwCheckTxt = "비밀번호가 일치하지 않습니다"
        val emailCheckError = "이미 사용중인 이메일입니다."
        val emailChecked = "사용가능한 이메일입니다."
        val plzCheckEmail = "이메일 중복확인을 해주세요."
        var isEmailChecked: Boolean = false

//        emailCheckBtn.setOnClickListener {
//            var userEmail = emailEt.text.toString()
//            val emailRequest = RequestRepository().requestEmail(email = userEmail)
//
//            emailRequest.enqueue(object : Callback<Boolean> {
//                override fun onResponse(
//                    call: Call<Boolean>,
//                    response: Response<Boolean>
//                ) {
//                    if (response.code() == 200) {
//                        // 성공 처리
//                        val result = response.body().toString()
//                        Log.d("email", result)
//                        if (result == "true") {
//                            isEmailChecked = false
//                            Toast.makeText(this@SignUpActivity, emailCheckError, Toast.LENGTH_SHORT)
//                                .show() //중복확인 메시지 띄우기
//                        } else {
//                            isEmailChecked = true
//                            Toast.makeText(this@SignUpActivity, emailChecked, Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    } else { // code == 400
//                        // 실패 처리
//                    }
//                }
//                override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                    Log.e("error", "${t.message}")
//                }
//            })
//        }

//        signupBtn.setOnClickListener {
//            var userEmail = emailEt.text.toString()
//            var userName = nameEt.text.toString()
//            var userPw = pwEt.text.toString()
//            var userPwCheck = pwChkEt.text.toString()
//
//            if (!userEmail.isNullOrEmpty() && !userName.isNullOrEmpty() && !userPw.isNullOrEmpty() && !userPwCheck.isNullOrEmpty()) {
//                if (isEmailChecked) {
//                    if (userPw == userPwCheck) {
//                        val signupRequest = RequestRepository().requestSignup(
//                            email = userEmail,
//                            name = userName,
//                            pw = userPw
//                        )
//                        signupRequest.enqueue(object : Callback<BaseResponse> {
//                            override fun onResponse(
//                                call: Call<BaseResponse>,
//                                response: Response<BaseResponse>
//                            ) {
//                                if (response.code() == 200) {
//                                    // 성공 처리
//                                    val msg: String = response.message()
//                                    Toast.makeText(this@SignUpActivity, msg, Toast.LENGTH_SHORT)
//                                        .show()
//
//                                    val intent =
//                                        Intent(this@SignUpActivity, LogInActivity::class.java)
//                                    startActivity(intent)
//                                } else { // code == 400
//                                    // 실패 처리
//                                }
//                            }
//                            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
//                                Log.e("error", "${t.message}")
//                            }
//                        })
//                    } else {
//                        Toast.makeText(applicationContext, pwCheckTxt, Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(applicationContext, plzCheckEmail, Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(applicationContext, txt, Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}