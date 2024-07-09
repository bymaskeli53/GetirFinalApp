package com.example.getirfinalapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductX::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getDao(): ProductDao

}