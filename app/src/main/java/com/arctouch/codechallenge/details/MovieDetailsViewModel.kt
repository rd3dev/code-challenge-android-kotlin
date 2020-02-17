package com.arctouch.codechallenge.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arctouch.codechallenge.data.MoviesRepository
import com.arctouch.codechallenge.model.Movie
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val movieDetailObserver = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> get() = movieDetailObserver

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            val detail = repository.getMovieDetails(id)
            movieDetailObserver.postValue(detail)
        }
    }
}