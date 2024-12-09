package com.example.getirfinalapp.network

import com.example.getirfinalapp.data.model.ProductItem
import com.example.getirfinalapp.data.model.ProductModelItem
import retrofit2.http.GET

const val BASE_URL = "https://65c38b5339055e7482c12050.mockapi.io/"
interface GetirApiService {

    @GET("api/products")
    suspend fun fetchProducts(): List<ProductModelItem>

    @GET("api/suggestedProducts")
    suspend fun fetchSuggestedProducts(): List<ProductItem>
}
