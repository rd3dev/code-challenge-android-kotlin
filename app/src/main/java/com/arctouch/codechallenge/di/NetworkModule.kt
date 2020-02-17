package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.api.TmdbApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun providesTmdbApi(retrofit: Retrofit): TmdbApi {
        val builder = retrofit.newBuilder().build()
        return builder.create(TmdbApi::class.java)
    }

    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    @Provides
    fun providesClient(logger: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }
}