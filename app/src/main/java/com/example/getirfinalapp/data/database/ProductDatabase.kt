package com.example.getirfinalapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.getirfinalapp.data.model.GeneralProductItem
import com.example.getirfinalapp.data.model.SuggestedProductItem

@Database(entities = [SuggestedProductItem::class, GeneralProductItem::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getDao(): ProductDao
}
