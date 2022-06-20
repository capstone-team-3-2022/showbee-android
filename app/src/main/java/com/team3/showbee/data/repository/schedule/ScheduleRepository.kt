package com.team3.showbee.data.repository.schedule

import com.team3.showbee.data.entity.*
//import com.team3.showbee.data.entity.Schedule
import com.team3.showbee.data.network.NetworkResponse

interface ScheduleRepository {
    suspend fun inviteUser(email: String) : NetworkResponse<InviteeResponse, ErrorResponse>
    suspend fun createSchedule(schedule: Schedule) : NetworkResponse<Int, ErrorResponse>
    suspend fun getCategoryIcon(nowDate : String) : NetworkResponse<Map<String, List<String>>, ErrorResponse>
    suspend fun getSList(nowDate: String) : NetworkResponse<MutableMap<String, MutableList<ScheduleContentModel>>, ErrorResponse>
    suspend fun updateSchedule(schedule: Schedule) : NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun getSchedule(sid: Long) : NetworkResponse<Schedule, ErrorResponse>
    suspend fun deleteSchedule(sid: Long): NetworkResponse<BaseResponse, ErrorResponse>
}