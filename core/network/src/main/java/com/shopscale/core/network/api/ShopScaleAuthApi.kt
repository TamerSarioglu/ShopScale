package com.shopscale.core.network.api

import com.shopscale.core.network.model.dto.LoginRequestDto
import com.shopscale.core.network.model.dto.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ShopScaleAuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): TokenResponseDto

    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Body refreshToken: String
    ): TokenResponseDto
}