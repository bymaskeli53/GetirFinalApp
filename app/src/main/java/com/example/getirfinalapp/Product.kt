package com.example.getirfinalapp

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("name")
    val productName: String,
    @SerializedName("attribute")
    val productAttribute: String,
    @SerializedName("priceText")
    val productPrice: String, // TODO: Price will be updated because we need to calculate basket price
    @SerializedName("imageURL")
    val productImage: String,
    val id: String,
)
