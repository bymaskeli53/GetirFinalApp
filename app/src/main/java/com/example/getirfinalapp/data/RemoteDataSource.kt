package com.example.getirfinalapp.data

import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import com.example.getirfinalapp.network.GetirApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: GetirApiService) {

    suspend fun fetchProducts(): List<ProductModelItem> = apiService.fetchProducts()

    suspend fun fetchSuggestedProducts(): List<ProductItem> = apiService.fetchSuggestedProducts()
}