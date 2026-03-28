@file:OptIn(kotlinx.serialization.InternalSerializationApi::class) // Eğer gerekiyorsa böyle yazılır
package com.shopscale.core.network.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)
@Serializable
data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String
)