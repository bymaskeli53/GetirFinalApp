package com.example.getirfinalapp.data.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.getirfinalapp.data.model.SuggestedProductItem
import com.example.getirfinalapp.data.model.GeneralProductItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(suggestedProductItem: SuggestedProductItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productX: GeneralProductItem): Long

    @Query("SELECT * FROM PRODUCT")
    fun getProducts(): Flow<List<GeneralProductItem>>


    @Query("SELECT * FROM PRODUCT WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: String): SuggestedProductItem?


    @Update
    suspend fun updateProduct(suggestedProductItem: SuggestedProductItem)

    @Update
    suspend fun updateProduct(productX: GeneralProductItem)

    @Query("Delete from PRODUCT")
    suspend fun deleteAllProducts()

}