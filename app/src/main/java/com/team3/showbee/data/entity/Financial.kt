package com.team3.showbee.data.entity

data class Financial(
    val fid: Long? = null,
    var date: String = "",
    var content: String? = null,
    var price: String = "",
    var category: String? = null,
    var bank: String? = null,
    var memo: String? = null,
    var inoutcome: Boolean = true
)
