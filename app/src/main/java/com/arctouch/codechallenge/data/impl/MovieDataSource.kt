package com.arctouch.codechallenge.data.impl

import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import retrofit2.Response

@ExperimentalCoroutinesApi
class MovieDataSource(private val tmdbApi: TmdbApi, private val scope: CoroutineScope) : PageKeyedDataSource<Int, Movie>() {

    private val _networkState: ConflatedBroadcastChannel<NetworkState> by lazy { ConflatedBroadcastChannel<NetworkState>() }

    val networkState: ConflatedBroadcastChannel<NetworkState>
        get() = _networkState

    private var genres: List<Genre> = listOf()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        val page = 1
        requestMovies(page) { moviesWithGenres ->
            callback.onResult(
                    moviesWithGenres, 0, moviesWithGenres.size, null,
                    page + 1
            )
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key

        requestMovies(page) { moviesWithGenres ->
            callback.onResult(moviesWithGenres, page + 1)
        }
    }

    private fun requestMovies(page: Int, callback: (moviesWithGenres: List<Movie>) -> Unit) {
        scope.launch {
            networkState.send(NetworkState.LOADING)
            try {
                val response = tmdbApi.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
                when {
                    response.isSuccessful -> {
                        networkState.send(NetworkState.LOADED)
                        setMoviesGenres(response)?.also { moviesWithGenres ->
                            callback(moviesWithGenres)
                        }
                    }
                    else -> networkState.send(NetworkState.error(response.message()))

                }
            } catch (exception: Exception) {
                networkState.send(NetworkState.error(exception.message))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            try {
                val response = tmdbApi.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                when {
                    response.isSuccessful -> {
                        networkState.send(NetworkState.LOADED)
                        genres = response.body()?.genres ?: listOf()
                    }
                    else -> networkState.send(NetworkState.error(response.message()))
                }
            } catch (exception: Exception) {
                networkState.send(NetworkState.error(exception.message))
            }
        }
    }

    private fun setMoviesGenres(response: Response<UpcomingMoviesResponse>): List<Movie>? {
        return response.body()?.results?.map { movie ->
            movie.copy(genres = genres.filter { movie.genreIds?.contains(it.id) == true })
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

}