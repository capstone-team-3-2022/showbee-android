package com.team3.showbee.data.network.api


import com.team3.showbee.data.entity.BaseResponse
import com.team3.showbee.data.entity.ErrorResponse
import com.team3.showbee.data.entity.Financial
import com.team3.showbee.data.network.NetworkResponse
import retrofit2.http.*

interface Service {
    @POST("v1/signin")
    suspend fun signInResponse(@Query("email") email: String,
                       @Query("password") password: String): NetworkResponse<BaseResponse, ErrorResponse>

    @PUT("v1/user/modify/name")
    suspend fun updateUsernameResponse(@Query("name") name:String): NetworkResponse<Boolean, ErrorResponse>

    @PUT("v1/user/modify/pwd")
    suspend fun updatePasswordResponse(@Query("password") password:String): NetworkResponse<Boolean, ErrorResponse>

    @DELETE("v1/user/delete")
    suspend fun deleteUserResponse() : NetworkResponse<BaseResponse, ErrorResponse>

    @POST("v1/signup")
    suspend fun signUpResponse(
        @Query("email") email: String,
        @Query("password") pw: String,
        @Query("name") name: String): NetworkResponse<BaseResponse, ErrorResponse>

    @GET("v1/check/{email}")
    suspend fun emailCheckResponse(@Path("email") email: String): NetworkResponse<Boolean, ErrorResponse>

    //---- financial
    @POST("v1/financial/post")
    suspend fun createFinancialResponse(
        @Body financial: Financial) : NetworkResponse<Int, ErrorResponse>

    @GET("v1/financial/lists")
    suspend fun getFinancialListResponse(): NetworkResponse<BaseResponse, ErrorResponse>

    @DELETE("v1/financial/delete/{fid}")
    suspend fun deleteFinancialResponse(@Path("fid") fid: Int): NetworkResponse<BaseResponse, ErrorResponse>
}