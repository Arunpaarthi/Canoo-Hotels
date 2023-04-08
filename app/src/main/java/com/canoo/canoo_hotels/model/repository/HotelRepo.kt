package com.canoo.canoo_hotels.model.repository

import com.canoo.canoo_hotels.model.data.HotelDetail
import com.canoo.canoo_hotels.model.data.HotelList
import com.canoo.canoo_hotels.model.data.SearchResult
import kotlinx.coroutines.flow.Flow

interface HotelRepo {

    suspend fun getHotels(city: String): Flow<SearchResult?>

    suspend fun getHotelList(regionId: String): Flow<HotelList?>

    suspend fun getHotelDetail(propertyId: String): Flow<HotelDetail?>
}