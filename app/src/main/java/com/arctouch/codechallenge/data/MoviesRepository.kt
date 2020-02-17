package com.arctouch.codechallenge.data

import androidx.paging.PagedList
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovieDetails(id: Long):Movie

    fun getUpcomingMoviesWithGenres(coroutineScope: CoroutineScope): PagedList<Movie>

    var upcomingMoviesRequestState: Flow<NetworkState>
}