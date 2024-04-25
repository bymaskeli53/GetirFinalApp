package com.example.getirfinalapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductXX(
    val attribute: String,
    val id: String,
    val imageURL: String,
    val name: String,
    val price: Double,
    val priceText: String,
    val shortDescription: String,
    val thumbnailURL: String
) : Parcelable