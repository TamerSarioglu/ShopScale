package com.shopscale.feature.productdetail.di

import com.shopscale.feature.productdetail.data.remote.api.ProductDetailApi
import com.shopscale.feature.productdetail.data.repository.ProductDetailRepositoryImpl
import com.shopscale.feature.productdetail.domain.repository.ProductDetailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductDetailNetworkModule {
    @Provides
    @Singleton
    fun provideProductDetailApi(retrofit: Retrofit): ProductDetailApi {
        return retrofit.create(ProductDetailApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductDetailRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductDetailRepository(
        impl: ProductDetailRepositoryImpl
    ): ProductDetailRepository
}