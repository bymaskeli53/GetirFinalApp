package com.example.getirfinalapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Product")
data class SuggestedProductItem(
    val category: String?,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageURL: String?,
    val name: String?,
    val price: Double?,
    val priceText: String?,
    val shortDescription: String?,
    val squareThumbnailURL: String?,
    val status: Int?,
    val unitPrice: Double?,
    var quantity: Int = 0
) : Parcelable
