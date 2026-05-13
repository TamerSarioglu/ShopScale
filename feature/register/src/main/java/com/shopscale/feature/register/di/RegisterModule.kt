package com.shopscale.feature.register.di

import com.shopscale.feature.register.data.remote.api.RegisterApi
import com.shopscale.feature.register.data.repository.RegisterRepositoryImpl
import com.shopscale.feature.register.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository
}

@Module
@InstallIn(SingletonComponent::class)
object RegisterApiModule {

    @Provides
    @Singleton
    fun provideRegisterApi(retrofit: Retrofit): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }
}
