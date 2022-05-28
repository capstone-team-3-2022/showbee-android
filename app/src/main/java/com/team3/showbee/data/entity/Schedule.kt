package com.team3.showbee.data.entity

data class Schedule(
    val stitle: String = "",
    val content: String? = null,
    val date: String = "",
    val price: Int = 0,
    val category: String? = null,
    val cycle: Int = 0,
    val shared: Boolean = false,
    val participant: ArrayList<String>,
    val inoutcome: Boolean = true
)
