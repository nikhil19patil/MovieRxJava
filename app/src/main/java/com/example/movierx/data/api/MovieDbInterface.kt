package com.example.movierx.data.api

import com.example.movierx.data.model.MovieDetails
import com.example.movierx.data.model.MovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieDbInterface {
    @GET("?")
    fun getMoviesList(@QueryMap map: HashMap<String, String>): Single<MovieListResponse>

    @GET("?")
    fun getMoviesDetails(@QueryMap map: HashMap<String, String>): Single<MovieDetails>
}