package com.example.getirfinalapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.getirfinalapp.data.model.ProductX
import com.example.getirfinalapp.data.model.ProductXX

@Database(entities = [ProductX::class, ProductXX::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getDao(): ProductDao

}