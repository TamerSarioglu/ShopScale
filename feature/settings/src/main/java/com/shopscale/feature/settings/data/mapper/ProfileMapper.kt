package com.shopscale.feature.settings.data.mapper

import com.shopscale.feature.settings.data.remote.dto.ProfileDto
import com.shopscale.feature.settings.domain.model.UserProfile

fun ProfileDto.toDomain(): UserProfile {
    return UserProfile(
        id = id,
        email = email,
        name = name,
        role = role,
        avatar = avatar
    )
}
