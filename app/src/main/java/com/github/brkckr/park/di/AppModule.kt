package com.github.brkckr.park.di

import com.github.brkckr.park.data.remote.ParkApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStockApi(): ParkApi {
        return Retrofit.Builder()
            .baseUrl(ParkApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()))
            .build()
            .create()
    }
}

