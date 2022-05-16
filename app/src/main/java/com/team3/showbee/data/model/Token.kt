package com.team3.showbee.data.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("data")
    val data: String
)