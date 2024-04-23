package com.example.getirfinalapp

import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: GetirApiService) {

    suspend fun fetchProducts(): List<Product> = apiService.fetchProducts()
//        return try {
//            val response = apiService.fetchProducts()
//            if (response.isSuccessful) {
//                Resource.Success(response.body())
//            } else {
//                Resource.Error(response.message())
//            }
//        } catch (e: Exception) {
//            Resource.Error(e.message ?: "An Error Occured")
//        }


    suspend fun fetchSuggestedProducts(): List<ProductItem> = apiService.fetchSuggestedProducts()
//        return try {
//            val response = apiService.fetchSuggestedProducts()
//            if (response.isSuccessful) {
//                Resource.Success(response.body())
//            } else {
//                Resource.Error(response.message())
//            }
//        } catch (e: Exception) {
//            Resource.Error(e.message ?: "An Error Occured")
//        }


}