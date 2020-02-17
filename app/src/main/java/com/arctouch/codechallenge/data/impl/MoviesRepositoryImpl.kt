package com.arctouch.codechallenge.data.impl

import androidx.paging.PagedList
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.MoviesRepository
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.NetworkState
import com.github.artgallery.util.MainThreadExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val api: TmdbApi) : MoviesRepository {

    override suspend fun getMovieDetails(id: Long): Movie {
        val response = api.movie(id,TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
            throw  Exception("Empty Body")
        }
        throw  Exception(response.message())
    }

    override lateinit var upcomingMoviesRequestState: Flow<NetworkState>

    @ExperimentalCoroutinesApi
    override fun getUpcomingMoviesWithGenres(coroutineScope: CoroutineScope): PagedList<Movie> {

        val dataSource = MovieDataSource(api, coroutineScope)
        upcomingMoviesRequestState = dataSource.networkState.asFlow()

        val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .build()

        return PagedList.Builder(dataSource, config)
                .setFetchExecutor(MainThreadExecutor())
                .setNotifyExecutor(MainThreadExecutor())
                .build()
    }

}