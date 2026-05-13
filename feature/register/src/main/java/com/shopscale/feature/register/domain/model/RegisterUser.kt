package com.shopscale.feature.register.domain.model

data class RegisterUser(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String
)
