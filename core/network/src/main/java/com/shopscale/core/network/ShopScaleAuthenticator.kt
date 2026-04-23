package com.shopscale.core.network

import android.util.Log
import com.shopscale.core.network.api.ShopScaleAuthApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class ShopScaleAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApi: Provider<ShopScaleAuthApi>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = tokenManager.getRefreshToken() ?: return null

        synchronized(this) {
            val currentToken = tokenManager.getAccessToken()

            if (response.request.header("Authorization") != "Bearer $currentToken") {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            return try {
                Log.w("ShopScaleNetwork", "401 Unauthorized! Attempting to refresh token...")

                val newTokenResponse = runBlocking {
                    authApi.get().refreshToken(
                        com.shopscale.core.network.model.dto.RefreshTokenRequestDto(refreshToken)
                    )
                }

                Log.w(
                    "ShopScaleNetwork",
                    "Token refreshed successfully! Retrying original request."
                )

                runBlocking {
                    tokenManager.saveTokens(
                        access = newTokenResponse.accessToken,
                        refresh = newTokenResponse.refreshToken
                    )
                }

                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newTokenResponse.accessToken}")
                    .build()

            } catch (e: Exception) {
                Log.e("ShopScaleNetwork", "Token refresh failed! Error: ${e.message}")
                runBlocking { tokenManager.clearTokens() }
                null
            }
        }
    }
}