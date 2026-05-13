package com.shopscale.feature.register.data.mapper

import com.shopscale.feature.register.data.remote.dto.RegisterUserRequestDto
import com.shopscale.feature.register.data.remote.dto.UserResponseDto
import com.shopscale.feature.register.domain.model.RegisterUser
import com.shopscale.feature.register.domain.model.RegisteredUser

fun RegisterUser.toDto(): RegisterUserRequestDto {
    return RegisterUserRequestDto(
        name = name,
        email = email,
        password = password,
        avatar = avatar
    )
}

fun UserResponseDto.toDomain(): RegisteredUser {
    return RegisteredUser(
        id = id,
        name = name,
        email = email,
        avatar = avatar,
        role = role
    )
}
