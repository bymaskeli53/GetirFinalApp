package com.example.getirfinalapp

import javax.inject.Inject

class GetirRepository @Inject constructor(val remoteDataSource: RemoteDataSource) {

    suspend fun fetchData(): List<ProductModelItem> {
        return remoteDataSource.fetchProducts()
    }

    suspend fun fetchSuggestedData(): List<ProductItem> {
        return remoteDataSource.fetchSuggestedProducts()
    }

}