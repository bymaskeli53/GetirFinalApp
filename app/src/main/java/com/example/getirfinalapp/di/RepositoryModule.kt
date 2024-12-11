package com.example.getirfinalapp.di

import com.example.getirfinalapp.ProductsRepository
import com.example.getirfinalapp.data.repository.ProductsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository
}
