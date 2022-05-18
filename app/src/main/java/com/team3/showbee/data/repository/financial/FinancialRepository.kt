package com.team3.showbee.data.repository.financial

import com.team3.showbee.data.entity.BaseResponse
import com.team3.showbee.data.entity.ErrorResponse
import com.team3.showbee.data.network.NetworkResponse

interface FinancialRepository {
    suspend fun createFinancial(
        date: String, content: String, price: String, category: String
    ) : NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun getFinancialList() : NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun deleteFinancial(fid: Int) : NetworkResponse<BaseResponse, ErrorResponse>
}