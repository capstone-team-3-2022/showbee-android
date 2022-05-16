package com.team3.showbee.data.network.api


import com.team3.showbee.data.entity.BaseResponse
import com.team3.showbee.data.entity.ErrorResponse
import com.team3.showbee.data.network.NetworkResponse
import retrofit2.http.*

interface Service {
    @POST("v1/signin")
    suspend fun signInResponse(@Query("email") email: String,
                       @Query("password") password: String): NetworkResponse<BaseResponse, ErrorResponse>

//    @POST("v1/signup")
//    fun signUpRequest(
//        @Query("email") email: String,
//        @Query("password") pw: String,
//        @Query("name") name: String): Call<BaseResponse>
//
//    @GET("v1/check/{email}")
//    fun emailCheck(@Path("email") email: String): Call<Boolean>
}