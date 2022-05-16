package com.team3.showbee.data.network


import com.team3.showbee.data.model.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @POST("v1/signin")
    fun signInRequest(@Query("email") email: String,
                      @Query("password") password: String): Call<BaseResponse>

    @POST("v1/signup")
    fun signUpRequest(
        @Query("email") email: String,
        @Query("password") pw: String,
        @Query("name") name: String): Call<BaseResponse>

    @GET("v1/check/{email}")
    fun emailCheck(@Path("email") email: String): Call<Boolean>
}