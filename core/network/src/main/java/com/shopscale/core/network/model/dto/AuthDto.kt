package com.shopscale.core.network.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class RegisterUserRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String
)

@Serializable
data class UserResponseDto(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val avatar: String,
    val role: String
)

@Serializable
data class TokenResponseDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String
)

@Serializable
data class RefreshTokenRequestDto(
    @SerialName("refreshToken") val refreshToken: String
)
