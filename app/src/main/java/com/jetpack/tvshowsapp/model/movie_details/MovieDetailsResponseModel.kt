package com.jetpack.tvshowsapp.model.movie_details


import com.google.gson.annotations.SerializedName

data class MovieDetailsResponseModel(
    @SerializedName("tvShow")
    val tvShow: TvShow
)