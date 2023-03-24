package com.jetpack.tvshowsapp.network.apis

import retrofit2.Response

/**
 * @Author: Giri Thangellapally
 * @Date: 17-03-2023
 */
abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            throw java.lang.Exception(error.toString())
        }
    }

}