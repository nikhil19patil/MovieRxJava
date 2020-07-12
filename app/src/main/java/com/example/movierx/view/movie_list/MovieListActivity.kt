package com.example.movierx.view.movie_list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierx.R
import com.example.movierx.data.api.MovieDbInterface
import com.example.movierx.data.api.RetrofitClient
import com.example.movierx.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieListViewModel

    lateinit var moviePagedListRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val apiService: MovieDbInterface = RetrofitClient.getClient()
        moviePagedListRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()

        val movieListAdapter = MovieRecyclerViewAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = movieListAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieListAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progressBar.visibility = if(viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            tvError.visibility = if(viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if(viewModel.listIsEmpty().not()) {
                movieListAdapter.setNetworkState(it)
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModel(): MovieListViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieListViewModel(moviePagedListRepository) as T
            }

        })[MovieListViewModel::class.java]
    }
}