package com.jetpack.tvshowsapp.utils

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun setImageURL(imageView: ImageView, URL: String) {
    try {
        imageView.alpha = 0f
        Picasso.get().load(URL).noFade().into(imageView, object : Callback {
            override fun onSuccess() {
                imageView.animate().setDuration(300).alpha(1f).start()
            }
            override fun onError(e: Exception?) {
                Log.v("IMAGE_ERROR",e.toString())
            }
        })
    } catch (e: java.lang.Exception) {

    }
}

