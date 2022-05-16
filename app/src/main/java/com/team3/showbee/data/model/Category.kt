package com.team3.showbee.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String
)