package com.example.movierx.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movierx.data.api.MovieDbInterface
import com.example.movierx.data.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: MovieDbInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, MovieDetails>() {

    val movieListDataSource = MutableLiveData<MovieListDataSource>()

    override fun create(): DataSource<Int, MovieDetails> {
        val movieDataSource = MovieListDataSource(apiService, compositeDisposable)

        movieListDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}