package com.example.movierx.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movierx.data.api.FIRST_PAGE
import com.example.movierx.data.api.MovieDbInterface
import com.example.movierx.data.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieListDataSource(private val apiService: MovieDbInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, MovieDetails>() {

    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieDetails>
    ) {
        val map = HashMap<String, String>()
        map["s"] = "avenger"
        map["page"] = page.toString()
        map["apikey"] = "457d975b"
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getMoviesList(map)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieListError", it.message!!)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDetails>) {
        val map = HashMap<String, String>()
        map["s"] = "avenger"
        map["page"] = params.key.toString()
        map["apikey"] = "457d975b"
        compositeDisposable.add(
            apiService.getMoviesList(map)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key) {
                            callback.onResult(it.movieList, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.END_OF_LIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieListError", it.message!!)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDetails>) {

    }
}