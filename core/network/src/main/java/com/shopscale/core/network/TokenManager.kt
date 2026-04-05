package com.shopscale.core.network

import javax.inject.Inject
import javax.inject.Singleton
import com.shopscale.core.datastore.ShopScalePreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@Singleton
class TokenManager @Inject constructor(
    private val preferences: ShopScalePreferences
) {
    fun getAccessToken(): String? = runBlocking {
        preferences.accessToken.firstOrNull()
    }

    fun getRefreshToken(): String? = runBlocking {
        preferences.refreshToken.firstOrNull()
    }

    suspend fun saveTokens(access: String, refresh: String) {
        preferences.saveTokens(access, refresh)
    }

    suspend fun clearTokens() {
        preferences.clearTokens()
    }
}