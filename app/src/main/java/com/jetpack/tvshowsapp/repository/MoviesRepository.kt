package com.jetpack.tvshowsapp.repository

import com.jetpack.tvshowsapp.model.movie_details.MovieDetailsResponseModel
import com.jetpack.tvshowsapp.model.movie_list.MoviesResponseModel
import com.jetpack.tvshowsapp.network.apis.ApiServices
import com.jetpack.tvshowsapp.network.apis.SafeApiRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val apiServices: ApiServices) :
    SafeApiRequest() {

    /**
     * Movies list*/
    suspend fun getMoviesList(page:Int): MoviesResponseModel {
        return apiRequest {
            apiServices.getMoviesList(page)
        }
    }

    /**
     * Movie Details by movieId
     * */
    suspend fun getMovieDetailsByMovieId(movieId: Int): MovieDetailsResponseModel {
        return apiRequest {
            apiServices.getMovieDetails(movieId)
        }
    }
}