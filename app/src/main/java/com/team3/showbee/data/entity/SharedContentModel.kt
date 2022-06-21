package com.team3.showbee.data.entity

data class SharedContentModel(
    val stitle: String = "",
    val content: String = "",
    val price: Int = 0,
    val date: String = "",
    val shared: Boolean = false,
    val cycle: Int = 0,
    val category: String = "",
    val inoutcome: Boolean = true,
    val sid: Long? = null
)