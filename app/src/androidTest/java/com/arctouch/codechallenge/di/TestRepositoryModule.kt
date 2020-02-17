package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.data.FakeMoviesRepositoryImpl
import com.arctouch.codechallenge.data.MoviesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class TestRepositoryModule {

    @Binds
    abstract fun provideMoviesRepository(repository: FakeMoviesRepositoryImpl): MoviesRepository
}