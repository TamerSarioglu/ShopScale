package com.shopscale.feature.settings.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: Int,
    val email: String,
    val name: String,
    val role: String,
    val avatar: String
)
