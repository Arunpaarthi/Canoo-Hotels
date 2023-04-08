package com.canoo.canoo_hotels.viewmodel

import androidx.lifecycle.viewModelScope
import com.canoo.canoo_hotels.model.data.*
import com.canoo.canoo_hotels.model.repository.HotelRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private var repo: HotelRepo) : HotelViewModel() {

    private var _hotelDetail = MutableStateFlow(
        getDefaultHotel()
    )
    val hotelDetail: StateFlow<HotelDetail> = _hotelDetail

    var id = ""
    
    private fun getDefaultHotel(): HotelDetail {
        return HotelDetail(
            hotelDetail = DataX(
                propertyInfo = PropertyInfo(
                    propertyGallery = PropertyGallery(emptyList()),
                    summary = Summary(id = "", name = "", tagline = "")
                )
            )
        )
    }

    fun setDefault() {
        _hotelDetail.value = getDefaultHotel()
    }

    fun getHotelDetail(propertyId: String) {
        id = propertyId
        setLoading()
        viewModelScope.launch {
            repo.getHotelDetail(propertyId).collect { hotelDetail ->
                if (hotelDetail != null) {
                    stopLoading()
                    _hotelDetail.value = hotelDetail
                } else {
                    setError()
                }
            }
        }
    }
}