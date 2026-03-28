package com.shopscale.core.network.di

import android.util.Log
import com.shopscale.core.network.ShopScaleAuthenticator
import com.shopscale.core.network.TokenManager
import com.shopscale.core.network.api.ShopScaleAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TAG = "ShopScaleNetwork"

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenManager: TokenManager,
        authenticator: ShopScaleAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = tokenManager.getAccessToken()

                val request = if (token != null) {
                    Log.d(TAG, "Adding Access Token to Request Header")
                    originalRequest.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                } else {
                    Log.d(TAG, "No Access Token found, sending request without header")
                    originalRequest
                }
                chain.proceed(request)
            }
            .authenticator(authenticator)
            .addInterceptor(HttpLoggingInterceptor { message ->
                Log.i("OkHttp", message)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): ShopScaleAuthApi =
        retrofit.create(ShopScaleAuthApi::class.java)
}