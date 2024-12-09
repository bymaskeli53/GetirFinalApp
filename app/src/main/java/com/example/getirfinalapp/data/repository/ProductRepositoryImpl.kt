package com.example.getirfinalapp.data.repository

import com.example.getirfinalapp.ProductsRepository
import com.example.getirfinalapp.data.RemoteDataSource
import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import com.example.getirfinalapp.network.ApiResult
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(val remoteDataSource: RemoteDataSource) :
    ProductsRepository {
    override suspend fun fetchProductList(): ApiResult<List<ProductModelItem>> {
        return remoteDataSource.fetchProducts()
    }

    override suspend fun fetchSuggestedProductList(): ApiResult<List<ProductItem>> {
        return remoteDataSource.fetchSuggestedProducts()
    }
}
