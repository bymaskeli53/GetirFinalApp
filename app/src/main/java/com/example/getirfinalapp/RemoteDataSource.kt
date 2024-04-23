package com.example.getirfinalapp

import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: GetirApiService) {

    suspend fun fetchProducts() : Resource<List<Product>>{
        return try {
            val response = apiService.fetchProducts()
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occured")
        }

    }
}