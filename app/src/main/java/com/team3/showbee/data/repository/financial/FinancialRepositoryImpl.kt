package com.team3.showbee.data.repository.financial

import com.team3.showbee.data.entity.BaseResponse
import com.team3.showbee.data.entity.ErrorResponse
import com.team3.showbee.data.entity.Financial
import com.team3.showbee.data.network.NetworkResponse
import com.team3.showbee.data.network.api.Service
import javax.inject.Inject

class FinancialRepositoryImpl @Inject constructor(
    private val service: Service
) : FinancialRepository {
    override suspend fun createFinancial(financial: Financial): NetworkResponse<Int, ErrorResponse> {
        return service.createFinancialResponse(financial)
    }

    override suspend fun getFinancialList(): NetworkResponse<BaseResponse, ErrorResponse> {
        return service.getFinancialListResponse()
    }

    override suspend fun deleteFinancial(fid: Int): NetworkResponse<BaseResponse, ErrorResponse> {
        return service.deleteFinancialResponse(fid)
    }
}