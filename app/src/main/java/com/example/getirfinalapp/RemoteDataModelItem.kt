package com.example.getirfinalapp

data class RemoteDataModelItem(
    val email: String,
    val id: String,
    val name: String,
    val password: String,
    val productCount: Int,
    val products: List<ProductX>
)