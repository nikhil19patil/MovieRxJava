package com.example.movierx.view.movie_details

import androidx.lifecycle.LiveData
import com.example.movierx.data.api.MovieDbInterface
import com.example.movierx.data.model.MovieDetails
import com.example.movierx.data.repository.MovieDetailsDataSource
import com.example.movierx.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import java.util.HashMap

class MovieDetailsRepository(private val apiService: MovieDbInterface) {

    lateinit var movieDetailsDataSource: MovieDetailsDataSource

    fun fetchMovieDetails(compositeDisposable: CompositeDisposable, map: HashMap<String, String>) : LiveData<MovieDetails> {
        movieDetailsDataSource = MovieDetailsDataSource(apiService, compositeDisposable);
        movieDetailsDataSource.fetchMovieDetails(map)

        return movieDetailsDataSource.downloadedMovieDetails
    }

    fun getNetworkState() : LiveData<NetworkState> {
        return movieDetailsDataSource.networkState
    }
}