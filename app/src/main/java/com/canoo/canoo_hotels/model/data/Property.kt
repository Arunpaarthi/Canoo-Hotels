package com.canoo.canoo_hotels.model.data

data class Property(
    val id: String,
    val name: String,
    val offerSummary: OfferSummary,
    val propertyImage: PropertyImage
)