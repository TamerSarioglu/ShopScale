package com.shopscale.feature.register.data.remote.api

import com.shopscale.feature.register.data.remote.dto.RegisterUserRequestDto
import com.shopscale.feature.register.data.remote.dto.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("users/")
    suspend fun registerUser(
        @Body request: RegisterUserRequestDto
    ): UserResponseDto
}
