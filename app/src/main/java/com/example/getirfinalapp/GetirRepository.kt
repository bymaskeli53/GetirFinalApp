package com.example.getirfinalapp

import javax.inject.Inject

class GetirRepository @Inject constructor(val remoteDataSource: RemoteDataSource) {

    suspend fun fetchData(): List<Product> {
        return remoteDataSource.fetchProducts()
    }

    suspend fun fetchSuggestedData(): List<ProductItem> {
        return remoteDataSource.fetchSuggestedProducts()
    }

}