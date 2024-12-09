package com.example.getirfinalapp.data

import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import com.example.getirfinalapp.network.ApiResult
import com.example.getirfinalapp.network.GetirApiService
import com.example.getirfinalapp.network.safeApiCall
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: GetirApiService) {

    suspend fun fetchProducts(): ApiResult<List<ProductModelItem>> {
        return safeApiCall {
            apiService.fetchProducts()
        }
    }

    suspend fun fetchSuggestedProducts(): ApiResult<List<ProductItem>> {
        return safeApiCall {
            apiService.fetchSuggestedProducts()
        }
    }
}
