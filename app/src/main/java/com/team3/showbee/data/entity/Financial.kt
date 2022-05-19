package com.team3.showbee.data.entity

data class Financial(
    val date: String = "",
    val content: String? = null,
    val price: Int = 0,
    val category: String? = null,
    val bank: String? = null,
    val memo: String? = null,
    val inoutcome: Boolean = true
)
