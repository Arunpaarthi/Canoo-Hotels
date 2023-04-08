package com.canoo.canoo_hotels.model.data

import com.google.gson.annotations.SerializedName

data class HotelList(
    @SerializedName("data")
    val hotelListObj: Data
)