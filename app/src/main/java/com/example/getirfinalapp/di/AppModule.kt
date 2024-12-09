package com.example.getirfinalapp.di

import android.content.Context
import androidx.room.Room
import com.example.getirfinalapp.ProductsRepository
import com.example.getirfinalapp.data.RemoteDataSource
import com.example.getirfinalapp.data.database.ProductDao
import com.example.getirfinalapp.data.database.ProductDatabase
import com.example.getirfinalapp.data.repository.ProductsRepositoryImpl
import com.example.getirfinalapp.network.BASE_URL
import com.example.getirfinalapp.network.GetirApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource): ProductsRepository {
        return ProductsRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(context, ProductDatabase::class.java, "product_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(db: ProductDatabase): ProductDao = db.getDao()
}
