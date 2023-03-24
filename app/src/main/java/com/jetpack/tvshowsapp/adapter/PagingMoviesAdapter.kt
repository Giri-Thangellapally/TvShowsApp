package com.jetpack.tvshowsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jetpack.tvshowsapp.databinding.ItemMoviesBinding
import com.jetpack.tvshowsapp.model.movie_list.MoviesResponseModel
import com.jetpack.tvshowsapp.utils.setImageURL
import javax.inject.Inject

class PagingMoviesAdapter @Inject constructor() :
    PagingDataAdapter<MoviesResponseModel.TvShow, PagingMoviesAdapter.MoviesViewHolder>(
        differCallback
    ) {
    private lateinit var binding: ItemMoviesBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        binding = ItemMoviesBinding.inflate(inflator, parent, false)
        context = parent.context
        return MoviesViewHolder()
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.set(getItem(position)!!)
        holder.setIsRecyclable(false)
    }


    inner class MoviesViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun set(item: MoviesResponseModel.TvShow) {
            binding.apply {
                textName.text = item.name
                textNetwork.text = item.network + "( ${item.country} )"
                textStarted.text = "Started On: " + item.startDate
                textStatus.text = item.status
                setImageURL(imgTVShow, item.imageThumbnailPath)
                root.setOnClickListener {
                    OnItemClickListner?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var OnItemClickListner: ((MoviesResponseModel.TvShow) -> Unit)? = null

    fun setOnClickListner(listener: (MoviesResponseModel.TvShow) -> Unit) {
        OnItemClickListner = listener
    }

    companion object {

        private val differCallback = object : DiffUtil.ItemCallback<MoviesResponseModel.TvShow>() {
            override fun areItemsTheSame(oldItem: MoviesResponseModel.TvShow, newItem: MoviesResponseModel.TvShow): Boolean {

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MoviesResponseModel.TvShow, newItem: MoviesResponseModel.TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}