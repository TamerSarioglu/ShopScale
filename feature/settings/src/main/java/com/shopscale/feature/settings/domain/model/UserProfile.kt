package com.shopscale.feature.settings.domain.model

data class UserProfile(
    val id: Int,
    val email: String,
    val name: String,
    val role: String,
    val avatar: String
)
