package com.team3.showbee.data.repository.schedule

import com.team3.showbee.data.entity.BaseResponse
import com.team3.showbee.data.entity.ErrorResponse
import com.team3.showbee.data.entity.Financial
import com.team3.showbee.data.entity.InviteeResponse
//import com.team3.showbee.data.entity.Schedule
import com.team3.showbee.data.network.NetworkResponse

interface ScheduleRepository {
    //suspend fun createSchedule(schedule : Schedule) : NetworkResponse<Int, ErrorResponse>
    suspend fun inviteUser(email: String) : NetworkResponse<InviteeResponse, ErrorResponse>
}