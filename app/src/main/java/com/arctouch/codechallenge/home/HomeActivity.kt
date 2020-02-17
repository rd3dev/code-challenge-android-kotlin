package com.arctouch.codechallenge.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.home_activity.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        val adapter = HomeAdapter()
        adapter.submitList(viewModel.movies)
        progressBar.visibility = View.GONE
        recyclerView.adapter = adapter
        setObserver(viewModel)
    }

    private fun setObserver(viewModel: HomeViewModel) {
        viewModel.showLoading.observe(this, Observer { showLoading ->
            progressBar.visibility = if (showLoading) View.VISIBLE else View.GONE
        })

        viewModel.showError.observe(this, Observer { error ->
            Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG)
        })
    }
}
