package com.example.getirfinalapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ProductX")
data class GeneralProductItem(
    val attribute: String?,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageURL: String?,
    val name: String?,
    val price: Double?,
    val priceText: String?,
    val shortDescription: String?,
    val thumbnailURL: String?,
    val quantity: Int?
) : Parcelable
