package com.team3.showbee.data.model

import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("list") val list: List<ListData>
)