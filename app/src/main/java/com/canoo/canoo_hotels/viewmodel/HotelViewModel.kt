package com.canoo.canoo_hotels.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class HotelViewModel: ViewModel() {
    internal var _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    internal var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun setLoading() {
        _loading.value = true
        _error.value = false
    }

    fun setError() {
        _loading.value = false
        _error.value = true
    }

    fun stopLoading() {
        _loading.value = false
        _error.value = false
    }
}