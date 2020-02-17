package com.arctouch.codechallenge.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.arctouch.codechallenge.data.MoviesRepository
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.NetworkState
import com.arctouch.codechallenge.model.Status
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var repository: MoviesRepository

    private lateinit var viewModel: HomeViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when HomeViewModel is started should get upcoming movies and update movies LiveData`() {
        val pagedList: PagedList<Movie> = mockk()

        coEvery { repository.getUpcomingMoviesWithGenres(any()) } returns pagedList
        viewModel = HomeViewModel(repository)
        val observer: Observer<List<Movie>> = mockk(relaxed = true)
        assert(viewModel.movies == pagedList)

    }

    @Test
    fun `when a view model initializes should show loading feedback to the user`() {
        coEvery { repository.upcomingMoviesRequestState } returns flow {
            emit(NetworkState.LOADING)
        }
        viewModel = HomeViewModel(repository)
        val loadingObserver: Observer<Boolean> = mockk(relaxed = true)
        viewModel.showLoading.observeForever(loadingObserver)

        verify(ordering = Ordering.UNORDERED) {
            loadingObserver.onChanged(true)
        }
    }

    @Test
    fun `when gets upcoming movies should hide loading feedback`() {
        coEvery { repository.upcomingMoviesRequestState } returns flow {
            emit(NetworkState.LOADED)
        }
        viewModel = HomeViewModel(repository)
        val loadingObserver: Observer<Boolean> = mockk(relaxed = true)
        viewModel.showLoading.observeForever(loadingObserver)

        verify(ordering = Ordering.UNORDERED) {
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `when gets upcoming movies failed should show error feedback`() {
        val error = "Error"

        coEvery { repository.upcomingMoviesRequestState } returns flow {
            emit(NetworkState.error(error))
        }
        viewModel = HomeViewModel(repository)
        val errorObserver: Observer<String> = mockk(relaxed = true)
        viewModel.showError.observeForever(errorObserver)

        verify(ordering = Ordering.UNORDERED) {
            errorObserver.onChanged(error)
        }
    }

}