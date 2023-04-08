package com.canoo.canoo_hotels.model.data

data class HotelDetailRequest(
    val currency: String = "USD",
    val eapid: Int = 1,
    val locale: String = "en_US",
    val propertyId: String,
    val siteId: Int = 300000001
)