package com.example.getirfinalapp.di

import com.example.getirfinalapp.data.RemoteDataSource
import com.example.getirfinalapp.network.BASE_URL
import com.example.getirfinalapp.network.GetirApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GetirApiService {
        return retrofit.create(GetirApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: GetirApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }
}
