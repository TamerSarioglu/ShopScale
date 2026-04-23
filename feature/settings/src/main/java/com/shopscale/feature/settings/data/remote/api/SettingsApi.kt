package com.shopscale.feature.settings.data.remote.api

import com.shopscale.feature.settings.data.remote.dto.ProfileDto
import retrofit2.http.GET

interface SettingsApi {
    @GET("auth/profile")
    suspend fun getProfile(): ProfileDto
}
