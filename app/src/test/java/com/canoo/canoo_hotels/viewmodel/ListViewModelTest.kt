package com.canoo.canoo_hotels.viewmodel

import com.canoo.canoo_hotels.MainDispatcherRule
import com.canoo.canoo_hotels.model.data.*
import com.canoo.canoo_hotels.model.repository.FakeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var listViewModel: ListViewModel
    private lateinit var fakeRepo: FakeRepo

    @Before
    fun setUp() {
        fakeRepo = FakeRepo()
        listViewModel = ListViewModel(fakeRepo)
    }

    @Test
    fun getHotels() = runTest {
        listViewModel.getHotels("New York")
        fakeRepo.emitSearchResult(
            SearchResult(
                cityName = "New York",
                rc = "OK",
                rid = "12143124"
            )
        )
        fakeRepo.emitHotelList(
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
        )
        val propertyList = listViewModel.propertyList.first()
        assertEquals(true, propertyList.size == 1 && propertyList[0].name == "Fake Hotel")
    }
}