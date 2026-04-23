package com.shopscale.feature.settings.domain.usecase

import com.shopscale.feature.settings.domain.model.UserProfile
import com.shopscale.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Result<UserProfile> {
        return repository.getProfile()
    }
}
