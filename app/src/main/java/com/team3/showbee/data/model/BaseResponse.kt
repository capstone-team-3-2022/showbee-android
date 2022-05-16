package com.team3.showbee.data.model

data class BaseResponse(
    val success: Boolean = false,
    val code: Int = -1,
    val msg: String = "",
    val data: String? = ""
)
