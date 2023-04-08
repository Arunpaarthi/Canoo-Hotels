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
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var fakeRepo: FakeRepo

    @Before
    fun setUp() {
        fakeRepo = FakeRepo()
        detailViewModel = DetailViewModel(fakeRepo)
    }

    @Test
    fun setDefault() = runTest {
        detailViewModel.setDefault()
        val hotelDetail = detailViewModel.hotelDetail.first()
        assertEquals(true, hotelDetail.hotelDetail.propertyInfo.summary.name == "")
    }

    @Test
    fun getHotelDetail() = runTest {
        val propertyId = "123124"
        detailViewModel.getHotelDetail(propertyId)
        fakeRepo.emitHotelDetail(
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
        )
        val hotelDetail = detailViewModel.hotelDetail.first()
        assertEquals(true, hotelDetail.hotelDetail.propertyInfo.summary.name == "Fake Hotel")
    }
}