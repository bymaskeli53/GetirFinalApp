package com.example.getirfinalapp

import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem

interface ProductsRepository {

    suspend fun fetchProductList(): List<ProductModelItem>

    suspend fun fetchSuggestedProductList(): List<ProductItem>
}