package com.canoo.canoo_hotels.model.data

data class HotelListRequest(
    val checkInDate: CheckInDate = CheckInDate(
        day = 6,
        month = 4,
        year = 2022
    ),
    val checkOutDate: CheckOutDate = CheckOutDate(
        day = 10,
        month = 4,
        year = 2022
    ),
    val currency: String = "USD",
    val destination: Destination,
    val eapid: Int = 1,
    val filters: Filters = Filters(
        PriceXX(
            min = 100,
            max = 1000
        )
    ),
    val locale: String = "en_US",
    val resultsSize: Int = 200,
    val resultsStartingIndex: Int = 0,
    val rooms: List<Room> = listOf(
        Room(
            adults = 2,
            children = listOf(
                Children(
                    1
                )
            )
        )
    ),
    val siteId: Int = 300000001,
    val sort: String = "PRICE_LOW_TO_HIGH"
)