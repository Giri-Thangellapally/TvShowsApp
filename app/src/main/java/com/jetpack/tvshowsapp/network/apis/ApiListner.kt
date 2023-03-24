package com.jetpack.tvshowsapp.network.apis

/**
 * @Author: Giri Thangellapally
 * @Date: 17-03-2023
 */
interface ApiListner {
    fun onStarted()
    fun onSuccess(data: Any)
    fun onFailure(message: String)
}