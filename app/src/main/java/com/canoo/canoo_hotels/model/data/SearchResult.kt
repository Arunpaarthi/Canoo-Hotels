package com.canoo.canoo_hotels.model.data

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("q")
    val cityName: String,
    val rc: String?,
    val rid: String
)
