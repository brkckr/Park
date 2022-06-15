package com.github.brkckr.park.di

import com.github.brkckr.park.data.repository.ParkRepositoryImpl
import com.github.brkckr.park.domain.repository.ParkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindParkRepository(
        parkRepositoryImpl: ParkRepositoryImpl
    ): ParkRepository
}