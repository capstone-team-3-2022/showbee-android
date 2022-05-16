package com.team3.showbee.data.repository


import com.team3.showbee.data.model.BaseResponse
import com.team3.showbee.di.ApiRequestFactory
import retrofit2.Call

class RequestRepository {

    fun requestSignIn(email: String,
                              password: String) = ApiRequestFactory.retrofit.signInRequest(email, password)
    fun requestSignup(
        email: String,
        name: String,
        pw: String): Call<BaseResponse> = ApiRequestFactory.retrofit.signUpRequest(email, name, pw)

    fun requestEmail(
        email: String
    ): Call<Boolean> = ApiRequestFactory.retrofit.emailCheck(email)

}