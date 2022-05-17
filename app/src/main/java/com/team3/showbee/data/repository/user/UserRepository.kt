package com.team3.showbee.data.repository.user

import com.team3.showbee.data.entity.BaseResponse
import com.team3.showbee.data.entity.ErrorResponse
import com.team3.showbee.data.network.NetworkResponse

interface UserRepository {
    suspend fun signUp(email:String, password: String, username: String): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun emailCheck(email:String): NetworkResponse<Boolean, ErrorResponse>
}