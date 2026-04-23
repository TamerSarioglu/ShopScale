package com.shopscale.feature.settings.domain.repository

import com.shopscale.feature.settings.domain.model.UserProfile

interface SettingsRepository {
    suspend fun getProfile(): Result<UserProfile>
    suspend fun logout()
}
