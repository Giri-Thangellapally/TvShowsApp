package com.jetpack.tvshowsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jetpack.tvshowsapp.model.movie_list.MoviesResponseModel
import com.jetpack.tvshowsapp.repository.MoviesRepository
import retrofit2.HttpException

/**
 * @Author: Giri Thangellapally
 * @Date: 23-03-2023
 */
class MoviePagingSource(private val moviesRepository: MoviesRepository) :
    PagingSource<Int, MoviesResponseModel.TvShow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesResponseModel.TvShow> {

        return try {
            val currentPage = params.key ?: 1
            val response = moviesRepository.getMoviesList(currentPage)
            val data = response.tvShows
            val responseData = mutableListOf<MoviesResponseModel.TvShow>()
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpE: HttpException) {
            LoadResult.Error(httpE)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, MoviesResponseModel.TvShow>): Int? {
        return null
    }

}