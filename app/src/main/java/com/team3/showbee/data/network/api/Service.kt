package com.team3.showbee.data.network.api


import com.team3.showbee.data.entity.*
import com.team3.showbee.data.network.NetworkResponse
import retrofit2.http.*
import retrofit2.http.Body

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

    @GET("v1/financial/get")
    suspend fun getFinancialResponse(@Query("fid") fid:Long): NetworkResponse<Financial, ErrorResponse>

    @GET("v1/financial/getMonthlyTotal")
    suspend fun getMonthlyTotalResponse(@Query("nowDate") nowDate:String): NetworkResponse<List<Long>, ErrorResponse>

    @GET("v1/financial/getMonthly")
    suspend fun getMonthlyResponse(@Query("nowDate") nowDate:String): NetworkResponse<Map<String, List<Long>>, ErrorResponse>

    @GET("v1/financial/getlist")
    suspend fun getListResponse(@Query("nowDate") nowDate:String): NetworkResponse<MutableMap<String, MutableList<FinancialContentModel>>, ErrorResponse>

    @PUT("v1/financial/modify")
    suspend fun updateFinancialResponse(@Body financial: Financial): NetworkResponse<BaseResponse, ErrorResponse>

    @DELETE("v1/financial/delete/{fid}")
    suspend fun deleteFinancialResponse(@Path("fid") fid: Long): NetworkResponse<BaseResponse, ErrorResponse>

    //----

    //schedule
    @GET("v1/user/get/{email}")
    suspend fun inviteUserEmailResponse(@Path("email")email: String): NetworkResponse<InviteeResponse, ErrorResponse>

    @POST("v1/schedule/post")
    suspend fun createScheduleResponse(
        @Body schedule: Schedule) : NetworkResponse<Int, ErrorResponse>

    @GET("v1/schedule/getMonthly")
    suspend fun getMonthlyCategoryResponse(@Query("nowDate") nowDate: String): NetworkResponse<Map<String, List<String>>, ErrorResponse>

    @GET("v1/schedule/getMonthlyTotal")
    suspend fun getSMonthlyTotalResponse(@Query("nowDate") nowDate:String): NetworkResponse<List<Long>, ErrorResponse>

    @GET("v1/schedule/getlist")
    suspend fun getSListResponse(@Query("nowDate") nowDate: String): NetworkResponse<MutableMap<String, MutableList<ScheduleContentModel>>, ErrorResponse>

    @PUT("v1/schedule/modify")
    suspend fun updateScheduleResponse(@Body schedule: Schedule): NetworkResponse<BaseResponse, ErrorResponse>

    @GET("v1/schedule/get")
    suspend fun getScheduleResponse(@Query("sid") sid: Long): NetworkResponse<Schedule, ErrorResponse>

    @DELETE("v1/schedule/delete/{sid}")
    suspend fun deleteScheduleResponse(@Path("sid") sid: Long): NetworkResponse<BaseResponse, ErrorResponse>
}