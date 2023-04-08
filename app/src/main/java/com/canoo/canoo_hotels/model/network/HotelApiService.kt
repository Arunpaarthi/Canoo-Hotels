package com.canoo.canoo_hotels.model.network

import com.canoo.canoo_hotels.model.data.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import javax.inject.Singleton

/**
 * [HotelApiService] class that is responsible for communicating with backend api and obtaining
 * data for the application
 * @author Arun Paarthi
 * */
@Singleton
class HotelApiService {

    private var api: HotelRapidAPi? = null

    private fun getApi(): HotelRapidAPi {
        return if (api == null) {
            val httpClient = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
            api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(HotelRapidAPi::class.java)
            api!!
        } else {
            api!!
        }
    }

    suspend fun getSearchResult(city: String): SearchResult? {
        return getApi().searchWithLocation(city)
    }

    suspend fun getListOfHotels(regionId: String): HotelList? {
        return getApi().getListOfHotels(
            HotelListRequest(
                destination = Destination(
                    regionId = regionId
                )
            )
        )
    }

    suspend fun getHotelDetail(id: String): HotelDetail? {
        return getApi().getHotelDetail(
            HotelDetailRequest(
                propertyId = id
            )
        )
    }

    companion object {
        // Canoo - eb07c2ad91msh79b8bfcf83611f4p13a2f5jsn39e78106a121
        // Mine - a57ecbf91emsh2cd09abf3fdcb61p11c25djsn48922309cccf
        private const val APP_ID = "a57ecbf91emsh2cd09abf3fdcb61p11c25djsn48922309cccf"
        private const val BASE_URL = "https://hotels4.p.rapidapi.com/"
        private const val API_KEY = "X-RapidAPI-Key"
        private const val API_HOST = "X-RapidAPI-Host"
        private const val API_HOST_VAL = "hotels4.p.rapidapi.com"
        val HEADERS = hashMapOf(
            API_KEY to APP_ID,
            API_HOST to API_HOST_VAL
        )
    }
}

interface HotelRapidAPi {

    //https://hotels4.p.rapidapi.com/locations/v3/search?q=new%20york
    @GET("locations/v3/search")
    suspend fun searchWithLocation(
        @Query("q") cityName: String,
        @HeaderMap headers: HashMap<String, String> = HotelApiService.HEADERS
    ): SearchResult?

    //https://hotels4.p.rapidapi.com/properties/v2/list
    @POST("properties/v2/list")
    suspend fun getListOfHotels(
        @Body data: HotelListRequest,
        @HeaderMap headers: HashMap<String, String> = HotelApiService.HEADERS
    ): HotelList?


    //https://hotels4.p.rapidapi.com/properties/v2/detail
    @POST("properties/v2/detail")
    suspend fun getHotelDetail(
        @Body data: HotelDetailRequest,
        @HeaderMap headers: HashMap<String, String> = HotelApiService.HEADERS
    ): HotelDetail?

}