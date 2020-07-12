package com.example.movierx.view.movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movierx.R
import com.example.movierx.data.api.MovieDbInterface
import com.example.movierx.data.api.RetrofitClient
import com.example.movierx.data.model.MovieDetails
import com.example.movierx.data.repository.NetworkState
import com.example.movierx.databinding.ActivityMovieDetailsBinding
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    lateinit var viewModel: MovieDetailsViewModel
    lateinit var movieDetailsRepository: MovieDetailsRepository
    lateinit var binding: ActivityMovieDetailsBinding

    fun getViewModel(map: HashMap<String, String>): MovieDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieDetailsViewModel(
                    movieDetailsRepository,
                    map
                ) as T
            }
        })[MovieDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        val movieId = intent.extras?.getString("movie_id") ?: "tt0848228"
        val map = HashMap<String, String>()
        map["i"] = movieId //"tt0848228"
        map["apikey"] = "457d975b"

        val apiService: MovieDbInterface = RetrofitClient.getClient()
        movieDetailsRepository =
            MovieDetailsRepository(
                apiService
            )
        viewModel = getViewModel(map)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.progressCircular!!.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
        })
    }

    fun bindUI(movie: MovieDetails) {
        binding.movie = movie
        binding.svMain?.visibility = View.VISIBLE
        supportActionBar?.title = movie.Title
    }
}