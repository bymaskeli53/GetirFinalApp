package com.example.getirfinalapp


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertProduct(productX: ProductX) : Long

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertProduct(productX: ProductXX) : Long

        @Query("SELECT * FROM PRODUCT")
        suspend fun getProducts(): List<ProductXX>



        @Update
        suspend fun updateProduct(productX: ProductX)

        @Update
        suspend fun updateProduct(productX: ProductXX)

        @Query("Delete from PRODUCT")
        suspend fun deleteAllProducts()

        // TODO: Update eklenece









}