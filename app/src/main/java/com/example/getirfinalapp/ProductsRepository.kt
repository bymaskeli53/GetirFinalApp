package com.example.getirfinalapp

import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import com.example.getirfinalapp.network.ApiResult

interface ProductsRepository {

    suspend fun fetchProductList(): ApiResult<List<ProductModelItem>>

    suspend fun fetchSuggestedProductList(): ApiResult<List<ProductItem>>
}
