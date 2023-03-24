package com.jetpack.tvshowsapp.network.apis

import com.jetpack.tvshowsapp.model.movie_details.MovieDetailsResponseModel
import com.jetpack.tvshowsapp.model.movie_list.MoviesResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("most-popular")
     suspend fun getMoviesList(@Query("page") page:Int) : Response<MoviesResponseModel>

    @GET("show-details")
    suspend fun getMovieDetails(@Query("q") p:Int): Response<MovieDetailsResponseModel>



}