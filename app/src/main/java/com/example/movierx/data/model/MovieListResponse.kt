package com.example.movierx.data.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("Search")
    val movieList: List<MovieDetails>,
    val totalResults: Int
) {
    val totalPages: Int
        get() = if (totalResults % 10 == 0) totalResults / 10 else totalResults / 10 + 1
}