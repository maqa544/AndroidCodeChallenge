package com.example.razorsyncdemo.base

import com.example.razorsyncdemo.model.Results
import retrofit2.Response

interface BaseSource {
    suspend fun <T> oneShotCalls(call: suspend () -> Response<T>): Results<T>
}