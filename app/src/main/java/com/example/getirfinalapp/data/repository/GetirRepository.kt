package com.example.getirfinalapp.data.repository

import com.example.getirfinalapp.data.RemoteDataSource
import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import javax.inject.Inject

class GetirRepository @Inject constructor(val remoteDataSource: RemoteDataSource) {

    suspend fun fetchData(): List<ProductModelItem> {
        return remoteDataSource.fetchProducts()
    }

    suspend fun fetchSuggestedData(): List<ProductItem> {
        return remoteDataSource.fetchSuggestedProducts()
    }



}