package com.shopscale.feature.settings.data.repository

import com.shopscale.core.network.TokenManager
import com.shopscale.feature.settings.data.mapper.toDomain
import com.shopscale.feature.settings.data.remote.api.SettingsApi
import com.shopscale.feature.settings.domain.model.UserProfile
import com.shopscale.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val api: SettingsApi,
    private val tokenManager: TokenManager
) : SettingsRepository {

    override suspend fun getProfile(): Result<UserProfile> {
        return try {
            val dto = api.getProfile()
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        tokenManager.clearTokens()
    }
}
