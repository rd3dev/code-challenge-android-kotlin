package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.data.MoviesRepository
import com.arctouch.codechallenge.data.impl.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository
}