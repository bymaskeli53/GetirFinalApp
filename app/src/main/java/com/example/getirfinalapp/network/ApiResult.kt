package com.example.getirfinalapp.network

sealed class ApiResult<out T>{
    data class Loading<out T>(val data: T? = null): ApiResult<T>()
    data class Success<out T>(val data: T? = null): ApiResult<T>()
    data class Error<out T>(val message: String, val data: T? = null): ApiResult<T>()

}