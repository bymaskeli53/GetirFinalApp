package com.example.getirfinalapp

import androidx.recyclerview.widget.RecyclerView
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertProduct(productX: ProductX) : Long

        @Query("SELECT * FROM PRODUCT")
        suspend fun getProducts(): List<ProductX>

        // TODO: Update eklenece









}