package com.shopscale.feature.settings.di

import com.shopscale.feature.settings.data.remote.api.SettingsApi
import com.shopscale.feature.settings.data.repository.SettingsRepositoryImpl
import com.shopscale.feature.settings.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object SettingsNetworkModule {

    @Provides
    @Singleton
    fun provideSettingsApi(retrofit: Retrofit): SettingsApi {
        return retrofit.create(SettingsApi::class.java)
    }
}
