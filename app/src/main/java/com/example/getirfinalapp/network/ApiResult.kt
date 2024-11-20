package com.example.getirfinalapp.network

import android.net.http.HttpException
import okio.IOException

sealed class ApiResult<out T> {
    data class Loading<out T>(val data: T? = null) : ApiResult<T>()
    data class Success<out T>(val data: T? = null) : ApiResult<T>()
    data class Error<out T>(val message: String, val data: T? = null) : ApiResult<T>()
    object NetworkError : ApiResult<Nothing>()
    object UnknownError : ApiResult<Nothing>()

}

// Generic API call handler
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): ApiResult<T> {
    return try {
        // Attempt the API call
        val result = apiCall()
        ApiResult.Success(result)
    } catch (e: retrofit2.HttpException) {
        // Handle HTTP exceptions (e.g., 404, 500)
        ApiResult.Error(
            message = e.message() ?: "An unexpected error occurred",
        )
    } catch (e: IOException) {
        // Handle network errors (e.g., no internet)
        ApiResult.NetworkError
    } catch (e: Exception) {
        // Handle any other unexpected errors
        ApiResult.UnknownError
    }
}