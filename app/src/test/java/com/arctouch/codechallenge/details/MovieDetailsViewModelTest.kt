package com.arctouch.codechallenge.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.data.MoviesRepository
import com.arctouch.codechallenge.model.Movie
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule

class MovieDetailsViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: MoviesRepository

    private lateinit var viewModel: MovieDetailsViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = MovieDetailsViewModel(repository)

    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when HomeViewModel is started should get upcoming movies and update movies LiveData`() {
        val id = 3L
        val movie = mockk<Movie>()
        coEvery { repository.getMovieDetails(id) } returns movie

        val observer: Observer<Movie> = mockk(relaxed = true)
        viewModel.movieDetails.observeForever (observer)

        viewModel.getMovieDetails(id)
        verify {  observer.onChanged(movie) }
    }
}