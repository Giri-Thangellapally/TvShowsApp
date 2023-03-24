package com.jetpack.tvshowsapp.model.movie_list


import com.google.gson.annotations.SerializedName

data class MoviesResponseModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("total")
    val total: String,
    @SerializedName("tv_shows")
    val tvShows: List<TvShow>
) {
    data class TvShow(
        @SerializedName("country")
        val country: String,
        @SerializedName("end_date")
        val endDate: Any,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image_thumbnail_path")
        val imageThumbnailPath: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("network")
        val network: String,
        @SerializedName("permalink")
        val permalink: String,
        @SerializedName("start_date")
        val startDate: String,
        @SerializedName("status")
        val status: String
    )
}