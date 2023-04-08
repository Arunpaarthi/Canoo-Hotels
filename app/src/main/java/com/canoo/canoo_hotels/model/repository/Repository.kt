package com.canoo.canoo_hotels.model.repository

import android.util.Log
import com.canoo.canoo_hotels.model.data.HotelDetail
import com.canoo.canoo_hotels.model.data.HotelList
import com.canoo.canoo_hotels.model.data.SearchResult
import com.canoo.canoo_hotels.model.network.HotelApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private var apiService: HotelApiService): HotelRepo {

    override suspend fun getHotels(city: String): Flow<SearchResult?> {
        return flow {
            emit(apiService.getSearchResult(city))
        }.catch {
            Log.d("Repository", "Error - ${it.message}")
            emit(null)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getHotelList(regionId: String): Flow<HotelList?> {
        return flow {
            emit(apiService.getListOfHotels(regionId))
        }.catch {
            Log.d("Repository", "Error - ${it.message}")
            emit(null)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getHotelDetail(propertyId: String): Flow<HotelDetail?> {
        return flow {
            emit(apiService.getHotelDetail(propertyId))
        }.catch {
            Log.d("Repository", "Error - ${it.message}")
            emit(null)
        }.flowOn(Dispatchers.IO)
    }
}