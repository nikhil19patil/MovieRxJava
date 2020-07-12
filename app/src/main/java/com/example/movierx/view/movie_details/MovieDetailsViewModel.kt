package com.example.movierx.view.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movierx.data.model.MovieDetails
import com.example.movierx.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(private val movieRepo: MovieDetailsRepository, map: HashMap<String, String>): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<MovieDetails> by lazy {
        movieRepo.fetchMovieDetails(compositeDisposable, map)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepo.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}