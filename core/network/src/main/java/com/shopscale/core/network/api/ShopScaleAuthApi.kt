package com.shopscale.core.network.api

import com.shopscale.core.network.model.dto.LoginRequestDto
import com.shopscale.core.network.model.dto.RefreshTokenRequestDto
import com.shopscale.core.network.model.dto.RegisterUserRequestDto
import com.shopscale.core.network.model.dto.TokenResponseDto
import com.shopscale.core.network.model.dto.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ShopScaleAuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): TokenResponseDto

    @POST("users/")
    suspend fun registerUser(
        @Body request: RegisterUserRequestDto
    ): UserResponseDto

    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequestDto
    ): TokenResponseDto
}
