package com.arctouch.codechallenge.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arctouch.codechallenge.data.MoviesRepository
import com.arctouch.codechallenge.model.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(repository: MoviesRepository) : ViewModel() {

    val movies = repository.getUpcomingMoviesWithGenres(viewModelScope)

    private val showLoadingObserver = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = showLoadingObserver

    private val showErrorObserver = MutableLiveData<String>()
    val showError: LiveData<String> = showErrorObserver
    init {
        viewModelScope.launch {
            repository.upcomingMoviesRequestState.collect {

                when(it.status) {
                    Status.RUNNING -> showLoadingObserver.postValue(true)
                    Status.SUCCESS -> showLoadingObserver.postValue(false)
                    Status.FAILED-> showErrorObserver.postValue(it.msg ?: "")
                }
            }
        }

    }

}