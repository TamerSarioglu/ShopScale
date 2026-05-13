package com.shopscale.feature.register.domain.model

data class RegisteredUser(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String,
    val role: String
)
