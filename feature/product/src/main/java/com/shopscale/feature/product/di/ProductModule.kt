package com.shopscale.feature.product.di

import com.shopscale.feature.product.data.remote.api.ProductApi
import com.shopscale.feature.product.data.repository.ProductRepositoryImpl
import com.shopscale.feature.product.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    companion object {
        @Provides
        @Singleton
        fun provideProductApi(retrofit: Retrofit): ProductApi {
            return retrofit.create(ProductApi::class.java)
        }
    }
}
