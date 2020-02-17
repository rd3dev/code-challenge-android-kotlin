package com.arctouch.codechallenge.di

import android.app.Application
import com.arctouch.codechallenge.TestAppApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    TestRepositoryModule::class]
)
interface TestAppComponent : AndroidInjector<TestAppApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): TestAppComponent.Builder

        fun build(): TestAppComponent
    }

}