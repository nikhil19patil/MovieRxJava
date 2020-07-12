package com.example.movierx.data.model

data class MovieDetails(
    val Title: String,
    val Year: Int,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Metascore: String,
    val imdbRating: String?,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: String
) {
    val rating: Float
        get() {
            return try {
                imdbRating?.toFloat() ?: 0.0f
            } catch (e: NumberFormatException) {
                0.0f
            }
        }

}