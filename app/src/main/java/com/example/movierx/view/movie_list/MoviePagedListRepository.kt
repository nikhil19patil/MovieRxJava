package com.example.movierx.view.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movierx.data.api.MovieDbInterface
import com.example.movierx.data.model.MovieDetails
import com.example.movierx.data.repository.MovieDataSourceFactory
import com.example.movierx.data.repository.MovieListDataSource
import com.example.movierx.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: MovieDbInterface) {

    lateinit var moviePagedList: LiveData<PagedList<MovieDetails>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<MovieDetails>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieListDataSource, NetworkState>(
            movieDataSourceFactory.movieListDataSource, MovieListDataSource::networkState
        )
    }
}