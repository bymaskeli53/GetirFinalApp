package com.example.getirfinalapp.data.repository

import com.example.getirfinalapp.ProductsRepository
import com.example.getirfinalapp.data.RemoteDataSource
import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(val remoteDataSource: RemoteDataSource) :
    ProductsRepository {

    override suspend fun fetchProductList(): List<ProductModelItem> {
        return remoteDataSource.fetchProducts()
    }

    override suspend fun fetchSuggestedProductList(): List<ProductItem> {
        return remoteDataSource.fetchSuggestedProducts()
    }

}