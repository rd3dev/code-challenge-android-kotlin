package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.details.MovieDetailsActivity
import com.arctouch.codechallenge.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun movieDetailsActivity(): MovieDetailsActivity
}