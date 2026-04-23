package com.shopscale.core.database.di

import android.content.Context
import androidx.room.Room
import com.shopscale.core.database.ShopScaleDatabase
import com.shopscale.core.database.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideShopScaleDatabase(
        @ApplicationContext context: Context
    ): ShopScaleDatabase {
        return Room.databaseBuilder(
            context,
            ShopScaleDatabase::class.java,
            "shopscale_database"
        ).build()
    }

    @Provides
    fun provideProductDao(database: ShopScaleDatabase): ProductDao {
        return database.productDao()
    }
}
