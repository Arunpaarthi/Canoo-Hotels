package com.canoo.canoo_hotels.model.repository

import com.canoo.canoo_hotels.MainDispatcherRule
import com.canoo.canoo_hotels.model.data.*
import com.canoo.canoo_hotels.model.network.HotelApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: Repository
    @Mock
    private lateinit var apiService: HotelApiService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = Repository(apiService)
    }

    @Test
    fun getHotels() = runTest {
        val city = "new york"
        val flow = repository.getHotels(city)
        Mockito.`when`(apiService.getSearchResult(city)).then {
            SearchResult(
                cityName = city,
                rc = "OK",
                rid = "12143124"
            )
        }
        val result = flow.first()
        assertEquals(true, result?.cityName == city && result.rc == "OK")
    }

    @Test
    fun getHotelsException() = runTest {
        val flow = repository.getHotels("test")
        Mockito.`when`(apiService.getSearchResult("test")).then {
            throw IOException("Test Exception")
        }
        assertEquals(true, flow.first() == null)
    }

    @Test
    fun getHotelList() = runTest {
        val regionId = "123234"
        val flow = repository.getHotelList(regionId)
        Mockito.`when`(apiService.getListOfHotels(regionId)).then {
            HotelList(
                Data(
                    PropertySearch(
                        listOf(
                            Property(
                                id = "1234",
                                name = "Fake Hotel",
                                offerSummary = OfferSummary(
                                    emptyList()
                                ),
                                propertyImage = PropertyImage(
                                    Image(
                                        url = "https://fake.url"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        }
        val result = flow.first()
        assertEquals(true, result?.hotelListObj?.propertySearch?.properties?.isNotEmpty())
    }

    @Test
    fun getHotelListException() = runTest {
        val flow = repository.getHotelList("test")
        Mockito.`when`(apiService.getListOfHotels("test")).then {
            throw IOException("Test Exception")
        }
        assertEquals(true, flow.first() == null)
    }

    @Test
    fun getHotelDetail() = runTest {
        val propertyId = "123123"
        val flow = repository.getHotelDetail(propertyId)
        Mockito.`when`(apiService.getHotelDetail(propertyId)).then {
            HotelDetail(
                DataX(
                    PropertyInfo(
                        PropertyGallery(
                            emptyList()
                        ),
                        Summary(
                            id = propertyId,
                            name = "Fake Hotel",
                            tagline = "The Best Hotel!!!"
                        )
                    )
                )
            )
        }
        val result = flow.first()
        assertEquals(true, result?.hotelDetail?.propertyInfo?.summary?.name == "Fake Hotel")
    }

    @Test
    fun getHotelDetailException() = runTest {
        val flow = repository.getHotelDetail("test")
        Mockito.`when`(apiService.getHotelDetail("test")).then {
            throw IOException("Test Exception")
        }
        assertEquals(true, flow.first() == null)
    }
}