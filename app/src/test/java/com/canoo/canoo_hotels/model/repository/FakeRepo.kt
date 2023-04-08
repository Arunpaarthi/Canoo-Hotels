package com.canoo.canoo_hotels.model.repository

import com.canoo.canoo_hotels.model.data.HotelDetail
import com.canoo.canoo_hotels.model.data.HotelList
import com.canoo.canoo_hotels.model.data.SearchResult
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeRepo: HotelRepo {
    private val searchResultFlow = MutableSharedFlow<SearchResult>()
    override suspend fun getHotels(city: String) = searchResultFlow
    suspend fun emitSearchResult(result: SearchResult) = searchResultFlow.emit(result)

    private val hotelListFlow = MutableSharedFlow<HotelList>()
    override suspend fun getHotelList(regionId: String) = hotelListFlow
    suspend fun emitHotelList(hotelList: HotelList) = hotelListFlow.emit(hotelList)

    private val detailFlow =  MutableSharedFlow<HotelDetail>()
    override suspend fun getHotelDetail(propertyId: String) = detailFlow
    suspend fun emitHotelDetail(hotelDetail: HotelDetail) = detailFlow.emit(hotelDetail)
}