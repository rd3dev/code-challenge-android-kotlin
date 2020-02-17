package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.data.impl.CacheImpl
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providesCache(): Cache {
       return CacheImpl
    }
}