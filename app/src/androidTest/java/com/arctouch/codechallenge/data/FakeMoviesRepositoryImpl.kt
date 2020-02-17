package com.arctouch.codechallenge.data

import androidx.paging.PagedList
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeMoviesRepositoryImpl @Inject constructor() : MoviesRepository {
    override var upcomingMoviesRequestState: Flow<NetworkState>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun getUpcomingMoviesWithGenres(coroutineScope: CoroutineScope): PagedList<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMovieDetails(id: Long): Movie = FakeData.movie

}