package com.shopscale.feature.settings.di

import com.shopscale.feature.settings.data.remote.api.SettingsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsApi(retrofit: Retrofit): SettingsApi {
        return retrofit.create(SettingsApi::class.java)
    }
}
