package com.shopscale.feature.settings.domain.usecase

import com.shopscale.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}
