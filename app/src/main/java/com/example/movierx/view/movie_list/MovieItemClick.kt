package com.example.movierx.view.movie_list

import com.example.movierx.data.model.MovieDetails

interface MovieItemClick {
    fun onMovieItemClick(movie: MovieDetails)
}