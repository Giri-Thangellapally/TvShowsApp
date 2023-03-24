package com.jetpack.tvshowsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jetpack.tvshowsapp.model.movie_details.MovieDetailsResponseModel
import com.jetpack.tvshowsapp.network.apis.ApiListner
import com.jetpack.tvshowsapp.paging.MoviePagingSource
import com.jetpack.tvshowsapp.repository.MoviesRepository
import com.jetpack.tvshowsapp.utils.Coroutines
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author: Giri Thangellapally
 * @Date: 16-03-2023
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    var apiListner: ApiListner? = null

    private  val TAG = "MoviesViewModel"
    lateinit var movieDetailsResponseModel: MovieDetailsResponseModel
    /**
     * All Movies List
     */

        val moviesResponse = Pager(PagingConfig(1)) {
            MoviePagingSource(moviesRepository)
        }.flow.cachedIn(viewModelScope)



    /**
     * Movie Details By Movie Id
     */
    fun getMovieDetailsById(movieId: Int) {
        Coroutines.main {
            val movieDetails = moviesRepository.getMovieDetailsByMovieId(movieId)
            try {
                movieDetails
                    .let {
                        apiListner?.onSuccess(movieDetails)
                        return@main
                    }
            } catch (e: java.lang.Exception) {
                apiListner?.onFailure(e.message.toString())
            }
        }
    }



}