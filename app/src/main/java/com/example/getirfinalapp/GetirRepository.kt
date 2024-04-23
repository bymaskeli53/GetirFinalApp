package com.example.getirfinalapp

import javax.inject.Inject

class GetirRepository @Inject constructor(val remoteDataSource: RemoteDataSource) {

    suspend fun fetchData(): Resource<List<Product>> {
        return remoteDataSource.fetchProducts()
    }

}