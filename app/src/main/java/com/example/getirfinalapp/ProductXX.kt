package com.example.getirfinalapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ProductX")
data class ProductXX(
    val attribute: String?,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageURL: String?,
    val name: String?,
    val price: Double?,
    val priceText: String?,
    val shortDescription: String?,
    val thumbnailURL: String?
) : Parcelable