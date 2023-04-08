package com.canoo.canoo_hotels.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.canoo.canoo_hotels.model.data.Property
import com.canoo.canoo_hotels.model.repository.HotelRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private var repo: HotelRepo) : HotelViewModel() {

    private var _propertyList = MutableStateFlow<List<Property>>(emptyList())
    val propertyList: StateFlow<List<Property>> = _propertyList

    fun setDefault() {
        _propertyList.value = emptyList()
    }

    var city = ""
    fun getHotels(cityName: String) {
        city = cityName
        setLoading()
        viewModelScope.launch {
            repo.getHotels(city).collect {
                if (it?.rc == "OK") {
                    Log.d("Test", "Test - Region Id ${it.rid}")
                    repo.getHotelList(it.rid).collect { hotelList ->
                        if (hotelList != null) {
                            stopLoading()
                            _propertyList.value =
                                hotelList.hotelListObj?.propertySearch?.properties ?: emptyList()
                        } else {
                            setError()
                        }
                    }
                } else {
                    setError()
                }
            }
        }
    }
}