package com.canoo.canoo_hotels.di

import com.canoo.canoo_hotels.model.network.HotelApiService
import com.canoo.canoo_hotels.model.repository.HotelRepo
import com.canoo.canoo_hotels.model.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HiltModule {

    @Binds
    @Singleton
    abstract fun getRepository(impl: Repository) : HotelRepo
}

@Module
@InstallIn(SingletonComponent::class)
object HiltProvider {

    @Provides
    @Singleton
    fun provideHotelApiService() : HotelApiService {
        return HotelApiService()
    }

}