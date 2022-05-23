package com.team3.showbee.data.repository.financial

import com.team3.showbee.data.entity.BaseResponse
import com.team3.showbee.data.entity.ErrorResponse
import com.team3.showbee.data.entity.Financial
import com.team3.showbee.data.network.NetworkResponse

interface FinancialRepository {
    suspend fun createFinancial(financial : Financial) : NetworkResponse<Int, ErrorResponse>
    suspend fun getMonthlyTotal(nowDate: String) : NetworkResponse<List<Long>, ErrorResponse>
    suspend fun deleteFinancial(fid: Int) : NetworkResponse<BaseResponse, ErrorResponse>
}