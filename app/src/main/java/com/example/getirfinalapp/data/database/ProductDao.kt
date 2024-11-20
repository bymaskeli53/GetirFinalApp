package com.example.getirfinalapp.data.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.getirfinalapp.data.model.ProductX
import com.example.getirfinalapp.data.model.ProductXX
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productX: ProductX): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productX: ProductXX): Long

    @Query("SELECT * FROM PRODUCT")
    fun getProducts(): Flow<List<ProductXX>>


    @Query("SELECT * FROM PRODUCT WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: String): ProductX?


    @Update
    suspend fun updateProduct(productX: ProductX)

    @Update
    suspend fun updateProduct(productX: ProductXX)

    @Query("Delete from PRODUCT")
    suspend fun deleteAllProducts()

}