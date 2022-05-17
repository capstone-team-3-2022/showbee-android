package com.team3.showbee.data.repository.user

import com.team3.showbee.data.entity.BaseResponse
import com.team3.showbee.data.entity.ErrorResponse
import com.team3.showbee.data.network.NetworkResponse
import com.team3.showbee.data.network.api.Service
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: Service
) : UserRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        username: String
    ): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.signUpResponse(email, password, username)
    }

    override suspend fun emailCheck(email: String): NetworkResponse<Boolean, ErrorResponse> {
        return  apiService.emailCheckResponse(email)
    }
}