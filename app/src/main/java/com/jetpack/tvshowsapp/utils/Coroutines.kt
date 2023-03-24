package com.jetpack.tvshowsapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author: Giri Thangellapally
 * @Date: 17-03-2023
 */
object Coroutines {
    /**
     * Creating Generic Coroutine class to perform long running operations
     * main fun takes a suspend function which do not take any parameter. Unit is equivalent as void
     * Here we are Dispatchers will decide on which thread it should execute.
     */
    fun main(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch {
        work()
    }
}