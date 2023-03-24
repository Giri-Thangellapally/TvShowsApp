package com.jetpack.tvshowsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jetpack.tvshowsapp.databinding.ItemContainerSliderImageBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImageSliderAdapter @Inject constructor(private val pictures: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    private lateinit var binding: ItemContainerSliderImageBinding
    private lateinit var context: Context


    inner class ImageViewHolder : RecyclerView.ViewHolder(binding.root) {
        public fun bindSliderImage(imageUrl: String) {
            setImageURL(binding.imgUrl, imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        binding = ItemContainerSliderImageBinding.inflate(inflator, parent, false)
        context = parent.context
        return ImageViewHolder()
    }

    override fun getItemCount(): Int = pictures.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindSliderImage(pictures[position])
    }

    fun setImageURL(imageView: ImageView, URL: String) {
        try {
            imageView.alpha = 0f
            Picasso.get().load(URL).noFade().into(imageView, object : Callback {
                override fun onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start()
                }

                override fun onError(e: Exception?) {
                    TODO("Not yet implemented")
                }

            })

        } catch (e: java.lang.Exception) {

        }
    }


}