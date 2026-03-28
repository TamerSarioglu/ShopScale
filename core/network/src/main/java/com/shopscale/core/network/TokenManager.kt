package com.shopscale.core.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor() {
    private var _accessToken: String? = null
    private var _refreshToken: String? = null

    fun getAccessToken(): String? = _accessToken
    fun getRefreshToken(): String? = _refreshToken

    fun saveTokens(access: String, refresh: String) {
        _accessToken = access
        _refreshToken = refresh
    }

    fun clearTokens() {
        _accessToken = null
        _refreshToken = null
    }
}