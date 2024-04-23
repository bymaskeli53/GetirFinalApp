package com.example.getirfinalapp

import retrofit2.Response
import retrofit2.http.GET


const val BASE_URL = "https://65c38b5339055e7482c12050.mockapi.io/"
interface GetirApiService {



    @GET("api/products")
    suspend fun fetchProducts(): Response<List<Product>>

}