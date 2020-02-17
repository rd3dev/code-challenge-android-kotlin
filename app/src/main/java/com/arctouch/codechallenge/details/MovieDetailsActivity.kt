package com.arctouch.codechallenge.details

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.movie_details_activity.*
import javax.inject.Inject

class MovieDetailsActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var factory:MovieDetailsViewModelFactory

    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)

        val id = intent.getLongExtra("ID", 0)
        val mViewModel = ViewModelProvider(this, factory).get(MovieDetailsViewModel::class.java)
        setDetailObserver(mViewModel)

        if(mViewModel.movieDetails.value == null) {
            mViewModel.getMovieDetails(id)
        }
    }

    private fun setDetailObserver(mViewModel: MovieDetailsViewModel) {
        mViewModel.movieDetails.observe(this, Observer {
            tv_movie_title.text = it.title
            tv_movie_details.text = getString(R.string.release_date_format, it.releaseDate, it.getGenesString())
            tv_movie_overview.text = it.overview
            Glide.with(this)
                    .load(it.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(iv_poster)

            Glide.with(this)
                    .load(it.backdropPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(iv_backdrop)
        })
    }
}