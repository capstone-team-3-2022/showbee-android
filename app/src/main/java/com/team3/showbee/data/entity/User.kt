package com.team3.showbee.data.entity

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("pw") val pw: String,
    @SerializedName("name") val name: String
)
