package com.shopscale.feature.register.data.remote.dto

import kotlinx.serialization.Serializable

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
